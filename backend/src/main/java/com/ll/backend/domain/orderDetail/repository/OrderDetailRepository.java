package com.ll.backend.domain.orderDetail.repository;

import com.ll.backend.domain.orderDetail.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
}
