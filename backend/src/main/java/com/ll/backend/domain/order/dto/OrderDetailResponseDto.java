package com.ll.backend.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderDetailResponseDto {
    private Integer order_detail_id;
    private Integer order_id;
    private ProductSummaryDto productSummaryDto;
    private Integer quantity;

    @Getter
    @AllArgsConstructor
    public static class ProductSummaryDto {
        private String name;
        private Integer price;
        private String imgPath;
    }
} 