package com.ll.backend.domain.orderdetail.controller;

import com.ll.backend.domain.orderdetail.dto.OrderDetailResponseDto;
import com.ll.backend.domain.orderdetail.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @GetMapping("/admin/order/{id}")
    public ResponseEntity<List<OrderDetailResponseDto>> getOrderDetail(@PathVariable int id){
        List<OrderDetailResponseDto> orderDetail = orderDetailService.getOrderDetail(id);
        if(orderDetail.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orderDetail, HttpStatus.OK);
    }

}
