package com.ll.backend.domain.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_name",unique = true, length = 20)
    private String name;

    private int price;

    private int quantity;

    private LocalDateTime created_date;

    private LocalDateTime modify_date;

    private String imagePath;



//    @ManyToOne
//    private Admin admin;

}
