package com.ll.backend.domain.order.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequestDto {
    private String email;
    private String address;
    private String postalCode;
    private Integer totalPrice;
    private List<OrderDetailRequestDto> products;
}
