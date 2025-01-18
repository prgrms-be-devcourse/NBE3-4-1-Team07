package com.ll.backend.domain.order.service;

import com.ll.backend.domain.order.OrderStatus;
import com.ll.backend.domain.order.dto.OrderDeliveryRequestDto;
import com.ll.backend.domain.order.dto.OrderRequestDto;
import com.ll.backend.domain.order.dto.OrderResponseDto;
import com.ll.backend.domain.order.entity.Order;
import com.ll.backend.domain.order.repository.OrderRepository;
import com.ll.backend.domain.orderdetail.service.OrderDetailService;
import com.ll.backend.domain.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderDetailService orderDetailService;


    // 주문 생성
    @Transactional
    public Order createOrder(OrderRequestDto orderRequestDto) {

        // 주문 정보 생성
        Order order = new Order(
                orderRequestDto.getEmail(),
                orderRequestDto.getAddress(),
                orderRequestDto.getPostalCode(),
                setOrderStateBasedOnTime(),
                orderRequestDto.getTotalPrice()
        );

        // 주문 저장
        return orderRepository.save(order);
    }

    private OrderStatus setOrderStateBasedOnTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalTime cutoffTime = LocalTime.of(14, 0); // 오후 2시 기준

        if (now.toLocalTime().isBefore(cutoffTime)) {
            return OrderStatus.SHIPPED;
        } else {
            return OrderStatus.PENDING;
        }
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

        for (Integer orderId : orderIds) {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("Order not found for ID: " + orderId));

            // 배송 상태 변경 로직
            if (isAfter2PM()) {
                order.setState(OrderStatus.DELIVERED); // 배송 상태로 변경
            } else {
                throw new IllegalArgumentException("Orders can only be processed after 2 PM.");
            }
        }
    }

    private boolean isAfter2PM() {
        LocalTime now = LocalTime.now();
        return now.isAfter(LocalTime.of(14, 0));
    }
}


