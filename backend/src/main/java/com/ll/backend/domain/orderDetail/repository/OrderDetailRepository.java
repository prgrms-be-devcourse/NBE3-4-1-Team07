package com.ll.backend.domain.orderDetail.repository;

import com.ll.backend.domain.order.entity.Order;
import com.ll.backend.domain.orderDetail.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
    List<OrderDetail> findByOrder(Order order);
}
