package com.ll.backend.domain.orderdetail.service;

import com.ll.backend.domain.orderdetail.dto.OrderDetailRequestDto;
import com.ll.backend.domain.order.entity.Order;
import com.ll.backend.domain.orderdetail.dto.OrderDetailResponseDto;
import com.ll.backend.domain.orderdetail.entity.OrderDetail;
import com.ll.backend.domain.orderdetail.repository.OrderDetailRepository;
import com.ll.backend.domain.product.entity.Product;
import com.ll.backend.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;

    public void createOrderDetails(Order order, List<OrderDetailRequestDto> products) {
        for(int i = 0; i < products.size(); i++) {
            Optional<Product> productOptional = productRepository.findById(products.get(i).getProductId());
            if(productOptional.isPresent()) {
                OrderDetail orderDetail = new OrderDetail(
                        order,
                        productOptional.get(),
                        products.get(i).getQuantity()
                );
                orderDetailRepository.save(orderDetail);
            }
        }
    }

    public List<OrderDetailResponseDto> getOrderDetail(int orderId) {

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder_Id(orderId);

        return orderDetails.stream()
                .map(detail -> new OrderDetailResponseDto(
                        detail.getProduct().getName(),
                        detail.getProduct().getPrice(),
                        detail.getQuantity(),
                        detail.getProduct().getImgPath()
                ))
                .collect(Collectors.toList());
    }

}
