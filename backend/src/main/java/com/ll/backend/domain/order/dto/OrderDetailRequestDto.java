package com.ll.backend.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderDetailRequestDto {
    private int productId;
    private int quantity;
}