package com.ll.backend.domain.order.service;

import com.ll.backend.domain.order.OrderStatus;
import com.ll.backend.domain.order.dto.OrderRequestDto;
import com.ll.backend.domain.order.dto.ProductOrderDto;
import com.ll.backend.domain.order.entity.Order;
import com.ll.backend.domain.order.repository.OrderRepository;
import com.ll.backend.domain.product.entity.Product;
import com.ll.backend.domain.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;


    // 주문 생성
    @Transactional
    public Order createOrder(OrderRequestDto orderRequestDto) {

        // 주문 정보 생성
        Order order = new Order(
                orderRequestDto.getEmail(),
                orderRequestDto.getAddress(),
                orderRequestDto.getPostalCode(),
                setOrderStateBasedOnTime(),
                orderRequestDto.getTotalPrice()
                );

        // 주문 저장
        return orderRepository.save(order);
    }

    private OrderStatus setOrderStateBasedOnTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalTime cutoffTime = LocalTime.of(14, 0); // 오후 2시 기준

        if (now.toLocalTime().isBefore(cutoffTime)) {
            return OrderStatus.SHIPPED;
        } else {
            return OrderStatus.PENDING;
        }
    }

    public long count() {
        return orderRepository.count();
    }

    public Order initDataOrder(Order order) {
        return orderRepository.save(order);
    }

    //결제 시 주문 상태 업데이트
//    @Transactional
//    public void processPayment(int orderId, OrderRequestDto orderRequestDto) {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new IllegalArgumentException("Order not found for ID: " + orderId));
//
//        // 14시 기준 상태 업데이트
//        setOrderStateBasedOnTime(order);
//
//        // 주문 상세 생성
//        createOrderDetails(order, orderRequestDto);
//
//        // 주문 저장
//        orderRepository.save(order);
//    }


//    @Transactional
//    public void updateOrderStatus(int orderId, Order.OrderStatus newState) {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new IllegalArgumentException("Order not found for ID: " + orderId));
//
//        validateStateTransition(order.getState(), newState);
//
//        order.setState(newState);
//        orderRepository.save(order);
//    }

//    private void validateStateTransition(Order.OrderStatus currentState, Order.OrderStatus newState) {
//        // 상태 전환 규칙 검증
//        if ((currentState == Order.OrderStatus.PENDING && newState != Order.OrderStatus.SHIPPED) ||
//                (currentState == Order.OrderStatus.SHIPPED && newState != Order.OrderStatus.DELIVERED)) {
//            throw new IllegalStateException("Invalid state transition from " + currentState + " to " + newState);
//        }
//    }

//    // 주문 상세 생성
//    private void createOrderDetails(Order order, OrderRequestDto orderRequestDto) {
//        for (OrderRequestDto.ProductOrderDto productDto : convertToProductOrderDto(orderRequestDto)) {
//            Product product = productRepository.findById(productDto.getProductId())
//                    .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productDto.getProductId()));
//
//            validateProductStock(product, productDto);
//
//            OrderDetail orderDetail = new OrderDetail();
//            orderDetail.setOrder(order);
//            orderDetail.setProduct(product);
//            orderDetail.setQuantity(productDto.getQuantity());
//            orderDetail.setTotalPrice(productDto.getQuantity() * product.getPrice());
//
//            product.setQuantity(product.getQuantity() - productDto.getQuantity());
//            productRepository.save(product);
//            orderDetailRepository.save(orderDetail);
//        }
//    }

    // 유효성 검증
    private void validateProductStock(Product product, ProductOrderDto productDto) {
        if (productDto.getQuantity() > product.getQuantity()) {
            throw new IllegalArgumentException("Requested quantity exceeds available stock for product ID: "
                    + productDto.getProductId() + ". Available stock: " + product.getQuantity());
        }
    }

    // 주문 상품 정보를 가져오는 메서드
    private List<ProductOrderDto> convertToProductOrderDto(OrderRequestDto orderRequestDto) {
        return orderRequestDto.getProducts();
    }



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


