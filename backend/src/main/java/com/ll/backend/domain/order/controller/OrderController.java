package com.ll.backend.domain.order.controller;

import com.ll.backend.domain.order.dto.OrderRequestDto;
import com.ll.backend.domain.order.entity.Order;
import com.ll.backend.domain.order.service.OrderService;
import com.ll.backend.domain.orderDetail.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderDetailService orderDetailService;

    record OrderCreated(Integer id,String message) {}

    //주문생성
    @PostMapping("/api/main/order")
    public ResponseEntity<OrderCreated> createOrder(@RequestBody OrderRequestDto orderRequestDto){
        Order order = orderService.createOrder(orderRequestDto);
        orderDetailService.createOrderDetails(order, orderRequestDto.getProducts());
        return ResponseEntity.ok(new OrderCreated(order.getId(),"Order successfully created."));
    }

    //결제완료
//    @PostMapping("/api/main/order/{orderId}")
//    public ResponseEntity<Map<String, String>> processPayment(@PathVariable int orderId, @RequestBody OrderRequestDto orderRequestDto) {
//        orderService.processPayment(orderId, orderRequestDto); // orderRequestDto 전달
//        adminNotificationService.notifyAdminForShipping(orderId);
//
//        // JSON 형태의 응답 생성
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "Payment processed and shipping request sent to admin.");
//        return ResponseEntity.ok(response);
//    }

//    record PatchStatus(Order.OrderStatus newState){}
//    @PatchMapping("/api/main/order/{orderId}/status")
//    public ResponseEntity<String> updateOrderStatus(
//            @PathVariable int orderId,
//            @RequestBody PatchStatus status) {
//        orderService.updateOrderStatus(orderId, status.newState);
//        return ResponseEntity.ok("Order status updated to: " + status.newState);
//    }

    //주문목록조회
//    @GetMapping("/admin/orderList")
//    public ResponseEntity<List<OrderResponseDto>> getOrderList(){
//        List<OrderResponseDto> orders=orderService.getAllOrders();
//        return ResponseEntity.ok(orders);
//    }
}
