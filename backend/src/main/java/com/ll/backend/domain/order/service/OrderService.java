package com.ll.backend.domain.order.service;

import com.ll.backend.domain.order.dto.OrderRequestDto;
import com.ll.backend.domain.order.dto.OrderResponseDto;
import com.ll.backend.domain.order.entity.Order;
import com.ll.backend.domain.order.repository.OrderRepository;
import com.ll.backend.domain.orderDetail.entity.OrderDetail;
import com.ll.backend.domain.orderDetail.repository.OrderDetailRepository;
import com.ll.backend.domain.product.entity.Product;
import com.ll.backend.domain.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;

    //주문생성
    @Transactional
    public Order createOrder(OrderRequestDto orderRequestDto) {
        // 주문 정보 생성
        Order order = new Order();
        order.setEmail(orderRequestDto.getEmail());
        order.setAddress(orderRequestDto.getAddress());
        order.setPostalCode(orderRequestDto.getPostalCode());
        order.setTotalPrice(orderRequestDto.getTotalPrice());
        order.setOrderDate(LocalDateTime.now());
        order.setState(Order.OrderStatus.PENDING);

        // 주문 저장
        Order savedOrder = orderRepository.save(order);

        // 주문 상세 저장
        for (OrderRequestDto.ProductOrderDto productDto : orderRequestDto.getProducts()) {
            Product product = productRepository.findById(productDto.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productDto.getProductId()));


            // 유효성 검증: 주문 수량이 재고를 초과하지 않는지 확인
            validateProductStock(product, productDto);

            // 주문 상세 생성
            OrderDetail orderDetail = createOrderDetail(savedOrder, product, productDto);

            // 재고 차감
            product.setQuantity(product.getQuantity() - productDto.getQuantity());
            productRepository.save(product);

            // 주문 상세 저장
            orderDetailRepository.save(orderDetail);
        }
        return savedOrder;
    }

    private void validateProductStock(Product product, OrderRequestDto.ProductOrderDto productDto) {
        if (productDto.getQuantity() > product.getQuantity()) {
            throw new IllegalArgumentException("Requested quantity exceeds available stock for product ID: " + productDto.getProductId()
                    + ". Available stock: " + product.getQuantity());
        }
    }

    private OrderDetail createOrderDetail(Order order, Product product, OrderRequestDto.ProductOrderDto productDto) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setQuantity(productDto.getQuantity());
        orderDetail.setTotalPrice(productDto.getQuantity() * product.getPrice());
        return orderDetail;
    }



    //주문목록조회
//    public List<OrderResponseDto> getAllOrders(){
//        return orderRepository.findAll().stream()
//                .map(order -> new OrderResponseDto(
//                        order.getId(),
//                        order.getEmail(),
//                        order.getAddress(),
//                        order.getPostalCode(),
//                        order.getState(),
//                        order.getTotalPrice(),
//                        order.getOrderDate()
//                ))
//                .collect(Collectors.toList());
//    }
}
