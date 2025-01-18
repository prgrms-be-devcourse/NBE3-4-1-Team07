package com.ll.backend.domain.order;

import com.ll.backend.domain.order.controller.OrderController;
import com.ll.backend.domain.order.dto.OrderResponseDto;
import com.ll.backend.domain.order.entity.Order;
import com.ll.backend.domain.order.repository.OrderRepository;
import com.ll.backend.domain.order.service.OrderService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;


import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderControllerTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("주문 생성")
    void t1() throws Exception{
        String requestBody = """
                {
                  "email": "test@example.com",
                  "address": "123 Test Street",
                  "postalCode": "12345",
                  "totalPrice": 100,
                  "products": [
                    {
                      "productId": 1,
                      "quantity": 2
                    },
                    {
                      "productId": 2,
                      "quantity": 1
                    }
                  ]
                }
                """;

        ResultActions resultActions = mvc.perform(
                post("/api/main/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .characterEncoding(StandardCharsets.UTF_8)
        ).andDo(print());

        // 응답 상태 코드 확인
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Order successfully created."));
    }

    @Test
    @DisplayName("주문 목록 조회")
    void t2() throws Exception{

        ResultActions resultActions = mvc.perform(
                get("/admin/orderList")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
        ).andDo(print());


        List<OrderResponseDto> orderListWithDetails = orderService.getOrderList();

        for (int i = 0; i < orderListWithDetails.size(); i++) {
            OrderResponseDto orderDto = orderListWithDetails.get(i);
            resultActions
                    .andExpect(handler().handlerType(OrderController.class))
                    .andExpect(handler().methodName("getOrderList"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[" + i + "].id").value(orderDto.getId()))
                    .andExpect(jsonPath("$[" + i + "].email").value(orderDto.getEmail()))
                    .andExpect(jsonPath("$[" + i + "].address").value(orderDto.getAddress()))
                    .andExpect(jsonPath("$[" + i + "].postalCode").value(orderDto.getPostalCode()))
                    .andExpect(jsonPath("$[" + i + "].state").value(orderDto.getState()))
                    .andExpect(jsonPath("$[" + i + "].totalPrice").value(orderDto.getTotalPrice()))
                    .andExpect(jsonPath("$[" + i + "].orderDate").value(Matchers.startsWith(orderDto.getOrderDate().toString().substring(0, 19))));
        }
        // 응답 상태 코드 확인
    }

    @Test
    @DisplayName("배송 상태 기능 구현")
    void t3() throws Exception {
        String requestBody = """
        {
            "id": [1, 2]
        }
        """;

        mvc.perform(put("/admin/delivery")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().string("Delivery status updated successfully."))
                .andDo(print());

        // 검증
        Optional<Order> order1 = orderRepository.findById(1);
        Optional<Order> order2 = orderRepository.findById(2);

        Assertions.assertEquals(OrderStatus.SHIPPED, order1.get().getState());
        Assertions.assertEquals(OrderStatus.SHIPPED, order2.get().getState());
        }
    }