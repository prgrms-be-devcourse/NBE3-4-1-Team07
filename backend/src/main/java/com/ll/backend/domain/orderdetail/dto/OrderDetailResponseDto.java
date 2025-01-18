package com.ll.backend.domain.orderdetail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderDetailResponseDto {
    private String name;
    private Integer price;
    private Integer quantity;
    private String imgPath;
} 