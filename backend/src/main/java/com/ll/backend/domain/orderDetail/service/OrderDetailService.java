package com.ll.backend.domain.orderDetail.service;

import com.ll.backend.domain.order.dto.OrderDetailRequestDto;
import com.ll.backend.domain.order.entity.Order;
import com.ll.backend.domain.orderDetail.entity.OrderDetail;
import com.ll.backend.domain.orderDetail.repository.OrderDetailRepository;
import com.ll.backend.domain.product.entity.Product;
import com.ll.backend.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

}
