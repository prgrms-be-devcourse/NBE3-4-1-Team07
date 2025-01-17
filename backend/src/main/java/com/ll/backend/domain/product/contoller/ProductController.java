package com.ll.backend.domain.product.contoller;

import com.ll.backend.domain.product.dto.ProductReqDto;
import com.ll.backend.domain.product.entity.Product;
import com.ll.backend.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
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

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/admin/product/create")
    public ResponseEntity<Product> createProduct(@RequestBody ProductReqDto productReqDto, Principal principal){
        Product product = productService.saveProduct(productReqDto, principal);
        if(product == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        URI location = URI.create("/admin/product/" + product.getId()); 
        return ResponseEntity.created(location).body(product);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/admin/product/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody ProductReqDto productReqDto){
        Product product = productService.modifyProduct(id, productReqDto);
        if(product == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/admin/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product success deleted", HttpStatus.OK);
    }

}
