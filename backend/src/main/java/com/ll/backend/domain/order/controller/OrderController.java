package com.ll.backend.domain.order.controller;

import com.ll.backend.domain.order.dto.OrderRequestDto;
import com.ll.backend.domain.order.entity.Order;
import com.ll.backend.domain.order.service.OrderService;
import com.ll.backend.domain.orderdetail.service.OrderDetailService;
import com.ll.backend.domain.order.dto.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/admin/orderList")
    public ResponseEntity<List<OrderResponseDto>> getOrderList() {
        List<OrderResponseDto> orderList = orderService.getOrderList();
        if (orderList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orderList);
    }

}
