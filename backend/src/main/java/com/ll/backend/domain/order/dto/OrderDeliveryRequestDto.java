package com.ll.backend.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDeliveryRequestDto {
    private List<Integer> id;
}
