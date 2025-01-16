package com.ll.backend.domain.product.dto;

import lombok.Getter;

@Getter
public class ProductRequestDto {
    private String name;
    private int price;
    private int quantity;
    private String imgPath;
}
