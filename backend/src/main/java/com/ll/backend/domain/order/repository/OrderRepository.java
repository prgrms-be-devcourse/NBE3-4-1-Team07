package com.ll.backend.domain.order.repository;

import com.ll.backend.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByEmail(String email);
}
