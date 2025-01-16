package com.ll.backend.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderResponseDto {
    private int id;
    private String email;
    private String postalCode;
    private String state;
    private int totalPrice;
    private LocalDateTime orderDate;
}
