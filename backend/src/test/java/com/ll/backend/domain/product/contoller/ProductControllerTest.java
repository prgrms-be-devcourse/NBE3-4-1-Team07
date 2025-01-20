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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
                    .andExpect(jsonPath("$[%d].created_date".formatted(i)).value(Matchers.startsWith(product.getCreated_date().toString().substring(0, 20))))
                    .andExpect(jsonPath("$[%d].modify_date".formatted(i)).value(Matchers.startsWith(product.getModify_date().toString().substring(0, 20))))
                    .andExpect(jsonPath("$[%d].imgPath".formatted(i)).value(product.getImgPath()));
        }
    }

    @Test
    @DisplayName("상품 등록")
    @WithMockUser(username = "user1")
    void t2() throws Exception {

        // given
        String name = "테스트 상품";
        int price = 10000;
        int quantity = 100;


        MockMultipartFile image = new MockMultipartFile(
                "image",
                "test.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "test image content".getBytes()
        );

        ResultActions resultActions = mvc.perform(
                        multipart("/admin/product/create")
                                .file(image)
                                .param("name", name)
                                .param("price", String.valueOf(price))
                                .param("quantity", String.valueOf(quantity))
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andDo(print());

        List<Product> productList = productService.findAll();
        assertFalse(productList.isEmpty(), "상품 리스트가 비어 있어서는 안 됩니다.");
        Product lastProduct = productList.get(productList.size() - 1);

        assertEquals("user1", lastProduct.getAdmin().getUsername());

        resultActions
                .andExpect(handler().handlerType(ProductController.class))
                .andExpect(handler().methodName("createProduct"))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.price").value(price))
                .andExpect(jsonPath("$.quantity").value(quantity))
                .andExpect(jsonPath("$.imgPath").exists());
    }

    @Test
    @DisplayName("상품 수정")
    @WithMockUser(username = "user1")
    void t3() throws Exception {
        int productId = 1;
        String name = "수정된 상품";
        int price = 20000;
        int quantity = 50;

        // Mock 이미지 파일 생성
        MockMultipartFile image = new MockMultipartFile(
                "image",
                "test.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "test image content".getBytes()
        );

        ResultActions resultActions = mvc.perform(
                        multipart("/admin/product/{id}", productId)
                                .file(image)
                                .param("name", name)
                                .param("price", String.valueOf(price))
                                .param("quantity", String.valueOf(quantity))
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .with(request -> {
                                    request.setMethod("PUT");
                                    return request;
                                })
                )
                .andDo(print());

        Product product;
        Optional<Product> productOptional = productService.findById(productId);
        if (productOptional.isPresent()) {
            product = productOptional.get();
        }else{
            product = new Product();
        }

        resultActions
                .andExpect(handler().handlerType(ProductController.class))
                .andExpect(handler().methodName("updateProduct"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.price").value(product.getPrice()))
                .andExpect(jsonPath("$.quantity").value(product.getQuantity()))
                .andExpect(jsonPath("$.imgPath").value(product.getImgPath()));
    }

    @Test
    @DisplayName("상품 삭제")
    @WithMockUser(username = "user1")
    void t4() throws Exception {
        int productId = 1;
        ResultActions resultActions = mvc
                .perform(delete("/admin/product/{id}", productId)
                        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
                .andDo(print());

        resultActions
                .andExpect(handler().handlerType(ProductController.class))
                .andExpect(handler().methodName("deleteProduct"))
                .andExpect(status().isOk())
                .andExpect(content().string("Product success deleted"));
    }
}



