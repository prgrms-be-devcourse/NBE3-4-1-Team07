package com.ll.backend.domain.product.contoller;

import com.ll.backend.domain.product.entity.Product;
import com.ll.backend.domain.product.service.ProductService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProductControllerTest {

    @Autowired
    private ProductService productService;
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("상품 목록 조회")

    void t1() throws Exception {
        ResultActions resultActions = mvc
                .perform(
                        get("/api/main/productList")
                )
                .andDo(print());

        resultActions
                .andExpect(handler().handlerType(ProductController.class))
                .andExpect(handler().methodName("getProductList"))
                .andExpect(status().isOk());

        List<Product> products = productService.findAll();

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            resultActions
                    .andExpect(jsonPath("$[%d].id".formatted(i)).value(product.getId()))
                    .andExpect(jsonPath("$[%d].name".formatted(i)).value(product.getName()))
                    .andExpect(jsonPath("$[%d].price".formatted(i)).value(product.getPrice()))
                    .andExpect(jsonPath("$[%d].quantity".formatted(i)).value(product.getQuantity()))
                    .andExpect(jsonPath("$[%d].created_date".formatted(i)).value(Matchers.startsWith(product.getCreated_date().toString())))
                    .andExpect(jsonPath("$[%d].modify_date".formatted(i)).value(Matchers.startsWith(product.getModify_date().toString())))
                    .andExpect(jsonPath("$[%d].imagePath".formatted(i)).value(product.getImagePath()));
        }
    }


}