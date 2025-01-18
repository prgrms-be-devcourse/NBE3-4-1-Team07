package com.ll.backend.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OrderResponseDto {
    private Integer id;
    private String email;
    private String address;
    private String postalCode;
    private String state;
    private Integer totalPrice;
    private LocalDateTime orderDate;
}
