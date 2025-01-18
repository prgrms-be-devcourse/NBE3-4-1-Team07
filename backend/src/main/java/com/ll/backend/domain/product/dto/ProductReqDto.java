package com.ll.backend.domain.product.dto;

import lombok.Getter;

@Getter
public class ProductReqDto {
    private String name;
    private int price;
    private int quantity;
    private String imgPath;

    public ProductReqDto(String name, int price, int quantity, String imgPath) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imgPath = imgPath;
    }
}
