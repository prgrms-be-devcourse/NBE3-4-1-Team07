package com.ll.backend.domain.order;

import com.ll.backend.domain.admin.service.AdminNotificationService;
import com.ll.backend.domain.order.controller.OrderController;
import com.ll.backend.domain.order.dto.OrderRequestDto;
import com.ll.backend.domain.order.dto.OrderResponseDto;
import com.ll.backend.domain.order.entity.Order;
import com.ll.backend.domain.order.service.OrderService;
import org.hamcrest.Matchers;
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
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderControllerTest {
//    @Autowired
//    private OrderService orderService;
//    @Autowired
//    private MockMvc mvc;
//    @Autowired
//    private AdminNotificationService adminNotificationService;
//
//    @Test
//    @DisplayName("장바구니 상품 정보를 바탕으로 주문 생성")
//    void t1() throws Exception{
//        String requestBody = """
//                {
//                  "email": "test@example.com",
//                  "address": "123 Test Street",
//                  "postalCode": "12345",
//                  "totalPrice": 100,
//                  "products": [
//                    {
//                      "productId": 1,
//                      "quantity": 2
//                    },
//                    {
//                      "productId": 2,
//                      "quantity": 1
//                    }
//                  ]
//                }
//                """;
//
//        ResultActions resultActions = mvc.perform(
//                post("/api/main/order")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody)
//                        .characterEncoding(StandardCharsets.UTF_8)
//        ).andDo(print());
//
//        // 응답 상태 코드 확인
//        resultActions
//                .andExpect(status().isOk())
//                .andExpect(content().string("Order successfully created."));
//    }
//
//    @Test
//    @DisplayName("결제 시 관리자에게 배송 요청")
//    void t2() throws Exception{
//        Long orderId = createSampleOrder();
//
//        ResultActions resultActions = mvc.perform(
//                post("/api/main/payment/" + orderId)
//        ).andDo(print());
//
//        // 응답 상태 코드 확인
//        resultActions
//                .andExpect(status().isOk())
//                .andExpect(content().string("Payment processed and admin notified for shipping."));
//    }

//    @Test
//    @DisplayName("주문 목록 조회")
//    void t3() throws Exception{
//
//        createSampleOrder();
//
//        ResultActions resultActions=mvc
//                .perform(
//                        get("/admin/orderList")
//                )
//                .andDo(print());
//
//        resultActions
//                .andExpect(handler().handlerType(OrderController.class))
//                .andExpect(handler().methodName("getOrderList"))
//                .andExpect(status().isOk());
//
//        List<OrderResponseDto> orders = orderService.getAllOrders();
//
//        for (int i = 0; i < orders.size(); i++) {
//            OrderResponseDto order = orders.get(i);
//            resultActions
//                    .andExpect(jsonPath("$[%d].id".formatted(i)).value(order.getId()))
//                    .andExpect(jsonPath("$[%d].email".formatted(i)).value(order.getEmail()))
//                    .andExpect(jsonPath("$[%d].address".formatted(i)).value(order.getAddress()))
//                    .andExpect(jsonPath("$[%d].postalCode".formatted(i)).value(order.getPostalCode()))
//                    .andExpect(jsonPath("$[%d].state".formatted(i)).value(order.getState()))
//                    .andExpect(jsonPath("$[%d].totalPrice".formatted(i)).value(order.getTotalPrice()))
//                    .andExpect(jsonPath("$[%d].orderDate".formatted(i)).value(Matchers.startsWith(
//                            order.getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))));
//        }
//
//    }

    //샘플 주문 생성
//    private Long createSampleOrder() {
//        OrderRequestDto orderRequest = new OrderRequestDto();
//        orderRequest.setEmail("test@example.com");
//        orderRequest.setAddress("123 Test Street");
//        orderRequest.setPostalCode("12345");
//        orderRequest.setTotalPrice(100);
//
//        OrderRequestDto.ProductOrderDto product1 = new OrderRequestDto.ProductOrderDto();
//        product1.setProductId(1L);
//        product1.setQuantity(2);
//
//        OrderRequestDto.ProductOrderDto product2 = new OrderRequestDto.ProductOrderDto();
//        product2.setProductId(2L);
//        product2.setQuantity(1);
//
//        orderRequest.setProducts(List.of(product1, product2));
//
//        Order order = orderService.createOrder(orderRequest);
//        return order.getOrderId();
//    }

}
