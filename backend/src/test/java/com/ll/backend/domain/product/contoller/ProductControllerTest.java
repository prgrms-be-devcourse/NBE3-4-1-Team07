package com.ll.backend.domain.product.contoller;

import com.ll.backend.domain.product.entity.Product;
import com.ll.backend.domain.product.service.ProductService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
                    .andExpect(jsonPath("$[%d].imgPath".formatted(i)).value(product.getImgPath()));
        }
    }

    @Test
    @DisplayName("상품 등록")
    void t2() throws Exception {
        ResultActions resultActions = mvc
                .perform(
                        post("/admin/product/create")
                                .content("""
                                        {
                                             "name" : "디카페인 원두",
                                             "price" : 12000,
                                             "quantity" : 3,
                                             "imgPath" : ""
                                        }
                                        """)
                                .contentType(
                                        new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
                                )
                )
                .andDo(print());

        List<Product> productList = productService.findAll();
        Product lastProduct= productList.getLast();

        resultActions
                .andExpect(handler().handlerType(ProductController.class))
                .andExpect(handler().methodName("createProduct"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(lastProduct.getName()))
                .andExpect(jsonPath("$.created_date").value(Matchers.startsWith(lastProduct.getCreated_date().toString().substring(0, 20))))
                .andExpect(jsonPath("$.modify_date").value(Matchers.startsWith(lastProduct.getModify_date().toString().substring(0, 20))))
                .andExpect(jsonPath("$.price").value(lastProduct.getPrice()))
                .andExpect(jsonPath("$.quantity").value(lastProduct.getQuantity()))
                .andExpect(jsonPath("$.imgPath").value(lastProduct.getImgPath()));
    }
}