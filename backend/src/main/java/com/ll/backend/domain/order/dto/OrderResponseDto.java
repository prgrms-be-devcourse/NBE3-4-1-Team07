package com.ll.backend.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponseDto {
    private int id;
    private String email;
    private String address;
    private String postalCode;
    private String state;
    private int totalPrice;
    private LocalDateTime orderDate;
}
