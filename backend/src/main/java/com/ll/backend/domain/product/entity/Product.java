package com.ll.backend.domain.product.entity;

import com.ll.backend.domain.admin.entity.Admin;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, length = 20)
    private String name;

    private int price;

    private int quantity;

    private LocalDateTime created_date;

    private LocalDateTime modify_date;

    private String imagePath;

    @ManyToOne
    private Admin admin;

}
