package com.ll.backend.domain.admin.service;

import com.ll.backend.domain.order.entity.Order;
import com.ll.backend.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminNotificationService {
    private final OrderRepository orderRepository;

    // 결제 완료 시 관리자에게 배송 요청 로직
    public void notifyAdminForShipping(Long orderId) {
        Order order = orderRepository.findById(orderId.intValue())
                .orElseThrow(() -> new IllegalArgumentException("Order not found for ID: " + orderId));


        // 배송 상태를 업데이트
        order.setState(Order.OrderStatus.SHIPPED);
        orderRepository.save(order);

        // 관리자에게 알림
        System.out.println("Admin notified for shipping. Order ID: " + orderId);
    }
}
