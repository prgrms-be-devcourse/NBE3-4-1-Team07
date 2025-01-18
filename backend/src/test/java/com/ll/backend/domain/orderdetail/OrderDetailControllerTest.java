package com.ll.backend.domain.orderdetail;

import com.ll.backend.domain.orderdetail.controller.OrderDetailController;
import com.ll.backend.domain.orderdetail.dto.OrderDetailResponseDto;
import com.ll.backend.domain.orderdetail.service.OrderDetailService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderDetailControllerTest {
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("주문 상세정보 조회")
    void t1() throws Exception {

        ResultActions resultActions = mvc.perform(
                get("/admin/order/" + "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
        ).andDo(print());

        List<OrderDetailResponseDto> orderDetails = orderDetailService.getOrderDetail(1);


        for (int i = 0; i < orderDetails.size(); i++) {
            OrderDetailResponseDto orderDetail = orderDetails.get(i);
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(handler().handlerType(OrderDetailController.class))
                    .andExpect(handler().methodName("getOrderDetail"))
                    .andExpect(jsonPath("$[" + i + "].name").value(orderDetail.getName()))
                    .andExpect(jsonPath("$[" + i + "].price").value(orderDetail.getPrice()))
                    .andExpect(jsonPath("$[" + i + "].quantity").value(orderDetail.getQuantity()))
                    .andExpect(jsonPath("$[" + i + "].imgPath").value(orderDetail.getImgPath()));
        }
    }

}




