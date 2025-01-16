package com.ll.backend.domain.order.service;

import com.ll.backend.domain.order.dto.OrderResponseDto;
import com.ll.backend.domain.order.entity.Order;
import com.ll.backend.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    //주문목록조회
    public List<OrderResponseDto> getAllOrders(){
        return orderRepository.findAll().stream()
                .map(order -> new OrderResponseDto(
                        order.getId(),
                        order.getEmail(),
                        order.getAddress(),
                        order.getPostalCode(),
                        order.getState(),
                        order.getTotalPrice(),
                        order.getOrderDate()
                ))
                .collect(Collectors.toList());
    }
}
