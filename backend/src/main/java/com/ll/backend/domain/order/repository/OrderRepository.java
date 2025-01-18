package com.ll.backend.domain.order.repository;

import com.ll.backend.domain.order.OrderStatus;
import com.ll.backend.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByEmail(String email);
    List<Order> findAllByStateAndOrderDate(OrderStatus state, LocalDateTime orderDate);;
}
