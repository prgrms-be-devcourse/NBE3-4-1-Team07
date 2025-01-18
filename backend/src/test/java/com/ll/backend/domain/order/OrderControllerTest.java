package com.ll.backend.domain.order;

import com.ll.backend.domain.order.controller.OrderController;
import com.ll.backend.domain.order.dto.OrderDetailResponseDto;
import com.ll.backend.domain.order.dto.OrderResponseDto;
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
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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


        List<OrderResponseDto> orderListWithDetails = orderService.getOrderListWithDetails();

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
                    .andExpect(jsonPath("$[" + i + "].orderDate").value(Matchers.startsWith(orderDto.getOrderDate().toString().substring(0, 19))))
                    .andExpect(jsonPath("$[" + i + "].products").isArray());

            // products 배열의 각 요소 검증
            for (int j = 0; j < orderDto.getProducts().size(); j++) {
                OrderDetailResponseDto detailDto = orderDto.getProducts().get(j);
                resultActions
                        .andExpect(jsonPath("$[" + i + "].products[" + j + "].order_detail_id").value(detailDto.getOrder_detail_id()))
                        .andExpect(jsonPath("$[" + i + "].products[" + j + "].order_id").value(detailDto.getOrder_id()))
                        .andExpect(jsonPath("$[" + i + "].products[" + j + "].productSummaryDto.name").value(detailDto.getProductSummaryDto().getName()))
                        .andExpect(jsonPath("$[" + i + "].products[" + j + "].productSummaryDto.price").value(detailDto.getProductSummaryDto().getPrice()))
                        .andExpect(jsonPath("$[" + i + "].products[" + j + "].productSummaryDto.imgPath").value(detailDto.getProductSummaryDto().getImgPath()))
                        .andExpect(jsonPath("$[" + i + "].products[" + j + "].quantity").value(detailDto.getQuantity()));
            }
        }
        // 응답 상태 코드 확인
    }

//    @Test
//    @DisplayName("결제 시 관리자에게 배송 요청")
//    void t2() throws Exception{
//        Long orderId = createSampleOrder();
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        // OrderRequestDto 생성
//        OrderRequestDto requestDto = new OrderRequestDto();
//        requestDto.setEmail("user@example.com");
//        requestDto.setAddress("123 Test Street");
//        requestDto.setPostalCode("12345");
//        requestDto.setTotalPrice(5000);
//
//        // ProductOrderDto 목록 생성
//        ProductOrderDto product1 = new ProductOrderDto();
//        product1.setProductId(1);
//        product1.setQuantity(2);
//
//        ProductOrderDto product2 = new ProductOrderDto();
//        product2.setProductId(2);
//        product2.setQuantity(1);
//
//        requestDto.setProducts(List.of(product1, product2));
//
//        // DTO를 JSON으로 변환
//        String requestJson = objectMapper.writeValueAsString(requestDto);
//
//        // POST 요청 수행
//        ResultActions resultActions = mvc.perform(
//                        post("/api/main/order/" + orderId)
//                                .contentType(MediaType.APPLICATION_JSON) // 요청 본문 타입 설정
//                                .content(requestJson) // 요청 본문 설정
//                )
//                .andDo(print());
//
//        // 응답 상태 코드 및 메시지 확인
//        resultActions
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value("Payment processed and shipping request sent to admin."));
//    }

    //하나는대기중상태인오더,하나는배송중인상태인오더
    //그리고 각 요청에 대해컨트롤러테스트
    //1번은성공해야되고 2번은실패해야됨
//    @Test
//    @DisplayName("주문 상태 업데이트")
//    void t3() throws Exception {
//
//        Long orderId = createSampleOrder();
//        //결제진행
//        // OrderRequestDto 생성
//        OrderRequestDto requestDto = new OrderRequestDto();
//        requestDto.setEmail("user@example.com");
//        requestDto.setAddress("123 Test Street");
//        requestDto.setPostalCode("12345");
//        requestDto.setTotalPrice(5000);
//
//        // ProductOrderDto 목록 생성
//        OrderRequestDto.ProductOrderDto product1 = new OrderRequestDto.ProductOrderDto();
//        product1.setProductId(1);
//        product1.setQuantity(2);
//
//        OrderRequestDto.ProductOrderDto product2 = new OrderRequestDto.ProductOrderDto();
//        product2.setProductId(2);
//        product2.setQuantity(1);
//
//        requestDto.setProducts(List.of(product1, product2));
//        //2시이전결제시shipped,이후결제시pending
//        orderService.processPayment(orderId.intValue(),requestDto);
//        LocalTime currentTime = LocalTime.now();
//        // 요청 본문 JSON 생성
//        String requestBody = """
//            {
//              "newState": "SHIPPED"
//            }
//            """;
//        ResultActions resultActions = mvc.perform(
//                MockMvcRequestBuilders.patch("/api/main/order/" + orderId + "/status")
//                        .content(requestBody)
//                        .contentType(MediaType.APPLICATION_JSON)
//        ).andDo(print());
//        if (currentTime.isBefore(LocalTime.of(14, 0))) {
//            // 14시 이전 테스트: 상태가 SHIPPED 변경없음,IllegalStateException처리
//
//            // 응답 상태 코드 확인
//            resultActions
//                    .andExpect(status().isOk())
//                    .andExpect(content().string("Order status remains PENDING after 14:00"));
//        } else {
//            // 14시 이후 테스트: 상태가 SHIPPED으로 변경
//            // 응답 상태 코드 확인
//            resultActions
//                    .andExpect(status().isOk())
//                    .andExpect(content().string("Order status updated to: SHIPPED"));
//
//        }
//    }

//    //샘플 주문 생성
//    private Long createSampleOrder() {
//        OrderRequestDto orderRequest = new OrderRequestDto();
//        orderRequest.setEmail("test@example.com");
//        orderRequest.setAddress("123 Test Street");
//        orderRequest.setPostalCode("12345");
//        orderRequest.setTotalPrice(100);
//
//        ProductOrderDto product1 = new ProductOrderDto();
//        product1.setProductId(1);
//        product1.setQuantity(2);
//
//        ProductOrderDto product2 = new ProductOrderDto();
//        product2.setProductId(2);
//        product2.setQuantity(1);
//
//        orderRequest.setProducts(List.of(product1, product2));
//
//        Order order = orderService.createOrder(orderRequest);
//        return order.getId().longValue(); // 주문 ID 반환
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


}
