package com.ll.backend.domain.order;

import com.ll.backend.domain.order.controller.OrderController;
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

    @Test
    @DisplayName("주문 목록 조회")
    void t1() throws Exception{
        ResultActions resultActions=mvc
                .perform(
                        get("/admin/orderList")
                )
                .andDo(print());

        resultActions
                .andExpect(handler().handlerType(OrderController.class))
                .andExpect(handler().methodName("getOrderList"))
                .andExpect(status().isOk());

        List<OrderResponseDto> orders = orderService.getAllOrders();

        for (int i = 0; i < orders.size(); i++) {
            OrderResponseDto order = orders.get(i);
            resultActions
                    .andExpect(jsonPath("$[%d].id".formatted(i)).value(order.getId()))
                    .andExpect(jsonPath("$[%d].email".formatted(i)).value(order.getEmail()))
                    .andExpect(jsonPath("$[%d].address".formatted(i)).value(order.getAddress()))
                    .andExpect(jsonPath("$[%d].postalCode".formatted(i)).value(order.getPostalCode()))
                    .andExpect(jsonPath("$[%d].state".formatted(i)).value(order.getState()))
                    .andExpect(jsonPath("$[%d].totalPrice".formatted(i)).value(order.getTotalPrice()))
                    .andExpect(jsonPath("$[%d].orderDate".formatted(i)).value(Matchers.startsWith(
                            order.getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))));
        }

    }

}
