package com.ll.backend.glodal.initData;

import com.ll.backend.domain.admin.entity.Admin;
import com.ll.backend.domain.admin.service.AdminService;
import com.ll.backend.domain.product.entity.Product;
import com.ll.backend.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {

    private final ProductService productService;
    private final AdminService adminService;

    @Autowired
    @Lazy
    private BaseInitData self;

    @Bean
    public ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
            self.work1();
            self.work2();
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

}
