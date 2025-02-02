package com.ll.backend.domain.order.entity;

import com.ll.backend.domain.order.OrderStatus;
import com.ll.backend.domain.orderdetail.entity.OrderDetail;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer id;

    @Column(length = 100)
    @Email
    private String email;

    @Column(length = 100)
    private String address;

    @Column(length = 20)
    private String postalCode;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private OrderStatus state;

    private int totalPrice;

    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails;

    public Order(String email, String address, String postalCode, OrderStatus state, int totalPrice) {
        this.email = email;
        this.address = address;
        this.postalCode = postalCode;
        this.state = state;
        this.totalPrice = totalPrice;
        this.orderDate = LocalDateTime.now();
    }


    public Order() {

    }
}
