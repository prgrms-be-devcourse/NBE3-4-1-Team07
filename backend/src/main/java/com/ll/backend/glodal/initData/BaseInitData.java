package com.ll.backend.glodal.initData;

import com.ll.backend.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {

    private final ProductService productService;

    @Autowired
    @Lazy
    private BaseInitData self;

    @Bean
    public ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
            self.work1();
        };
    }

    @Transactional
    public void work1() {
        if (productService.count() > 0) return;

//        Admin admin = new Admin();
        productService.join("콜롬비아 원두", 5000, 3, "");
        productService.join("브라질 원두", 7000, 5, "");
        productService.join("아라비카 원두", 10000, 5, "");
        productService.join("케냐 원두", 7000, 5, "");

    }
}
