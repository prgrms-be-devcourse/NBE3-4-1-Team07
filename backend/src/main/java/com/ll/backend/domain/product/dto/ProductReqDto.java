package com.ll.backend.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductReqDto {
    private String name;
    private int price;
    private int quantity;
}
