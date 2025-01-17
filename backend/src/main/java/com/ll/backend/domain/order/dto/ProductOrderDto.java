package com.ll.backend.domain.order.dto;

import lombok.Getter;

@Getter
public class ProductOrderDto{
    private int productId;
    private int quantity;

    public ProductOrderDto(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}