package com.ll.backend.domain.order.controller;

import com.ll.backend.domain.order.entity.Order;
import com.ll.backend.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/orderList")
    public List<Order> getOrderList(){
        return orderService.getAllOrders();
    }
}
