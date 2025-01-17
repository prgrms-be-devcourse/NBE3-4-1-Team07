package com.ll.backend.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDto {
    private String email;
    private String address;
    private String postalCode;
    private Integer totalPrice;
    private List<ProductOrderDto> products;

    @Getter
    @Setter
    public static class ProductOrderDto{
        private int productId;
        private Integer quantity;
    }
}
