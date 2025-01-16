package com.ll.backend.domain.order.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    @Email
    private String email;

    @Column(length = 100)
    private String address;

    @Column(length = 20)
    private String postalCode;

    @Column(length = 50)
    private String state;

    private int totalPrice;

    private LocalDateTime orderDate;

    public Order(String email, String address, String postalCode, String state, int totalPrice, LocalDateTime orderDate) {
        this.email = email;
        this.address = address;
        this.postalCode = postalCode;
        this.state = state;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }

    public Order(){
    }
}
