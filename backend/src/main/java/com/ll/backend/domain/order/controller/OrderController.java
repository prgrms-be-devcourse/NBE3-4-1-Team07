package com.ll.backend.domain.order.controller;

import com.ll.backend.domain.admin.service.AdminNotificationService;
import com.ll.backend.domain.order.dto.OrderRequestDto;
import com.ll.backend.domain.order.dto.OrderResponseDto;
import com.ll.backend.domain.order.entity.Order;
import com.ll.backend.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final AdminNotificationService adminNotificationService;
    record OrderCreated(Integer id,String message) {}
    //주문생성
    @PostMapping("/api/main/order")
    public ResponseEntity<OrderCreated> createOrder(@RequestBody OrderRequestDto orderRequestDto){
        Order order = orderService.createOrder(orderRequestDto);
        return ResponseEntity.ok(new OrderCreated(order.getId(),"Order successfully created."));
    }

    //결제완료
    @PostMapping("/api/main/order/{orderId}")
    public ResponseEntity<String> processPayment(@PathVariable int orderId, @RequestBody OrderRequestDto orderRequestDto) {
        orderService.processPayment(orderId, orderRequestDto); // orderRequestDto 전달
        adminNotificationService.notifyAdminForShipping(orderId);
        return ResponseEntity.ok("Payment processed and shipping request sent to admin.");
    }
    record PatchStatus(Order.OrderStatus newState){}

    @PatchMapping("/api/main/order/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable int orderId,
            @RequestBody PatchStatus status) {
        orderService.updateOrderStatus(orderId, status.newState);
        return ResponseEntity.ok("Order status updated to: " + status.newState);
    }

    //주문목록조회
//    @GetMapping("/admin/orderList")
//    public ResponseEntity<List<OrderResponseDto>> getOrderList(){
//        List<OrderResponseDto> orders=orderService.getAllOrders();
//        return ResponseEntity.ok(orders);
//    }
}
