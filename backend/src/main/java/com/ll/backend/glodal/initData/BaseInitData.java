package com.ll.backend.glodal.initData;

import com.ll.backend.domain.admin.entity.Admin;
import com.ll.backend.domain.admin.service.AdminService;
import com.ll.backend.domain.order.OrderStatus;
import com.ll.backend.domain.order.dto.ProductOrderDto;
import com.ll.backend.domain.order.entity.Order;
import com.ll.backend.domain.order.service.OrderService;
import com.ll.backend.domain.orderDetail.service.OrderDetailService;
import com.ll.backend.domain.product.entity.Product;
import com.ll.backend.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {

    private final ProductService productService;
    private final AdminService adminService;
    private final OrderService orderService;
    private final OrderDetailService orderDetailService;

    @Autowired
    @Lazy
    private BaseInitData self;

    @Bean
    public ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
            self.work1(); //user
            self.work2(); //product
            self.work3(); //order
        };
    }
    @Transactional
    public void work1() {
        if (adminService.count() > 0) return;
        adminService.registerAdmin("user1", "1234");
    }

    @Transactional
    public void work2() {
        if (productService.count() > 0) return;

        Optional<Admin> admin = adminService.getAdmin("user1");
        if (admin.isPresent()) {
            Product product1 = new Product("콜롬비아 원두", 5000, 3, "", admin.get());
            Product product2 = new Product("브라질 원두", 7000, 5, "", admin.get());
            Product product3 = new Product("아라비카 원두", 10000, 5, "", admin.get());
            Product product4 = new Product("케냐 원두", 7000, 5, "", admin.get());

            productService.initDataProduct(product1);
            productService.initDataProduct(product2);
            productService.initDataProduct(product3);
            productService.initDataProduct(product4);
        }
    }

    @Transactional
    public void work3() {
        if (orderService.count() > 0) return;

        List<ProductOrderDto> productOrderDtoList = new ArrayList<>();
        ProductOrderDto productOrderDto1 = new ProductOrderDto(1,3);
        ProductOrderDto productOrderDto2 = new ProductOrderDto(2,4);
        productOrderDtoList.add(productOrderDto1);
        productOrderDtoList.add(productOrderDto2);

        Order order1 = new Order(
                "asd123@naver.com",
                "서울시 강남구 청담동",
                "11111",
                OrderStatus.PENDING,
                39000
        );

        Order order2 = new Order(
                "zxc789@naver.com",
                "대전광역시 서구 둔산동",
                "22222",
                OrderStatus.PENDING,
                59000
        );


        orderDetailService.createOrderDetails(orderService.initDataOrder(order1), productOrderDtoList);
        orderDetailService.createOrderDetails(orderService.initDataOrder(order2), productOrderDtoList);
    }

}
