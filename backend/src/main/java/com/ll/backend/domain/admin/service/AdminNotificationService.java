package com.ll.backend.domain.admin.service;

import com.ll.backend.domain.order.dto.OrderRequestDto;
import com.ll.backend.domain.order.entity.Order;
import com.ll.backend.domain.order.repository.OrderRepository;
import com.ll.backend.domain.orderDetail.entity.OrderDetail;
import com.ll.backend.domain.orderDetail.repository.OrderDetailRepository;
import com.ll.backend.domain.product.entity.Product;
import com.ll.backend.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminNotificationService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;

    // 결제 완료 시 관리자에게 배송 요청 로직
    public void notifyAdminForShipping(int orderId) {
        // 주문 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found for ID: " + orderId));

        // 관리자에게 알림
        System.out.println("Admin notified for shipping. Order ID: " + orderId);
    }

}
