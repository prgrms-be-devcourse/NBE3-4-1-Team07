package com.ll.backend.domain.product.contoller;

import com.ll.backend.domain.product.dto.ProductReqDto;
import com.ll.backend.domain.product.entity.Product;
import com.ll.backend.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

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
    @RequestMapping("/admin/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id, Principal principal){
        Optional<Product> product = productService.findById(id);

        return product.map(value ->
                new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/admin/product/create")
    public ResponseEntity<Product> createProduct(@ModelAttribute ProductReqDto productReqDto,
                                                 @RequestPart("image") MultipartFile image, Principal principal){
        String defulatImgPath = "uploads/none.png";
        if(!image.isEmpty()){
            defulatImgPath = productService.saveImage(image);
        }
        Product product = productService.saveProduct(productReqDto, defulatImgPath, principal);
        if(product == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        URI location = URI.create("/admin/product/" + product.getId()); 
        return ResponseEntity.created(location).body(product);
    }


    @PutMapping("/admin/product/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id,
                                                 @RequestBody ProductReqDto productReqDto
                                                 //@RequestPart("image") MultipartFile image
    ){
        String defulatImgPath = "/uploads/none.png";
//        if(!image.isEmpty()){
//            defulatImgPath = productService.saveImage(image);
//        }
        Product product = productService.modifyProduct(id, productReqDto, defulatImgPath);
        if(product == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }


    @DeleteMapping("/admin/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product success deleted", HttpStatus.OK);
    }

}
