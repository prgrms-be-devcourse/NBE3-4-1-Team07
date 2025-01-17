package com.ll.backend.glodal.initData;

import com.ll.backend.domain.order.OrderStatus;
import com.ll.backend.domain.order.entity.Order;
import com.ll.backend.domain.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@RequiredArgsConstructor
public class OrderInitData {

    private final OrderRepository orderRepository;

    @Autowired
    @Lazy
    private OrderInitData self;

    @Bean
    public ApplicationRunner orderInitDataApplicationRunner() {
        return args -> {
            self.insertInitOrders();
        };
    }

    @Transactional
    public void insertInitOrders() {
        if (orderRepository.count() > 0)
            return;

        orderRepository.save(new Order(
                "test@example.com",
                "123 Street Name",
                "12345",
                OrderStatus.SHIPPED,
                5000
        ));
        orderRepository.save(new Order(
                "user@example.com",
                "456 Another St",
                "67890",
                OrderStatus.SHIPPED,
                7000
        ));
        orderRepository.save(new Order(
                "customer@example.com",
                "789 Last Lane",
                "13579",
                OrderStatus.SHIPPED,
                10000
        ));
    }
}
