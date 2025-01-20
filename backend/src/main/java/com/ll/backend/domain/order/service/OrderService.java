package com.ll.backend.domain.order.service;

import com.ll.backend.domain.order.OrderStatus;
import com.ll.backend.domain.order.dto.MailForm;
import com.ll.backend.domain.order.dto.OrderDeliveryRequestDto;
import com.ll.backend.domain.order.dto.OrderRequestDto;
import com.ll.backend.domain.order.dto.OrderResponseDto;
import com.ll.backend.domain.order.entity.Order;
import com.ll.backend.domain.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MailService mailService;



    // 주문 생성
    @Transactional
    public Order createOrder(OrderRequestDto orderRequestDto) {
        // 주문 정보 생성
        Order order = new Order(
                orderRequestDto.getEmail(),
                orderRequestDto.getAddress(),
                orderRequestDto.getPostalCode(),
                OrderStatus.PENDING,
                orderRequestDto.getTotalPrice()
        );
        // 주문 저장
        return orderRepository.save(order);
    }

    public long count() {
        return orderRepository.count();
    }

    public Order initDataOrder(Order order) {
        return orderRepository.save(order);
    }

    @Transactional()
    public List<OrderResponseDto> getOrderList() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map(order -> new OrderResponseDto(
                        order.getId(),
                        order.getEmail(),
                        order.getAddress(),
                        order.getPostalCode(),
                        order.getState().toString(),
                        order.getTotalPrice(),
                        order.getOrderDate()
                ))
                .collect(Collectors.toList());
    }

    //배송 상태 변경
    @Transactional
    public void updateDeliveryStatus(OrderDeliveryRequestDto orderDeliveryRequestDto){
        List<Integer> orderIds = orderDeliveryRequestDto.getId();

        if (orderIds == null || orderIds.isEmpty()) {
            throw new IllegalArgumentException("ID list cannot be null or empty.");
        }

        for (Integer orderId : orderIds) {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("Order not found for ID: " + orderId));

            // 배송 상태를 SHIPPED로 변경
            order.setState(OrderStatus.SHIPPED);


            MailForm mailForm = mailService.createMail(order.getEmail(), order.getOrderDetails());
            mailService.sendMail(mailForm);
        }
    }

}


