package com.ll.backend.domain.product.contoller;

import com.ll.backend.domain.product.dto.ProductRequestDto;
import com.ll.backend.domain.product.entity.Product;
import com.ll.backend.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/api/main/productList")
    public ResponseEntity<List<Product>> getProductList(){
        List<Product> productList = this.productService.findAll();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PostMapping("/admin/product/create")
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequestDto productRequestDto){
        Product product = productService.saveProduct(productRequestDto);
        if(product == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        URI location = URI.create("/admin/product/" + product.getId()); 
        return ResponseEntity.created(location).body(product);
    }
}
