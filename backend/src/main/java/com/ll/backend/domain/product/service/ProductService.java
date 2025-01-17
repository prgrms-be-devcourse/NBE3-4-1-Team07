package com.ll.backend.domain.product.service;

import com.ll.backend.domain.admin.entity.Admin;
import com.ll.backend.domain.admin.repository.AdminRepository;
import com.ll.backend.domain.product.dto.ProductReqDto;
import com.ll.backend.domain.product.entity.Product;
import com.ll.backend.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;

    public long count() {
        return productRepository.count();
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

    //ALTER TABLE PRODUCT AUTO_INCREMENT=1;
    //SET @COUNT = 0;
    //UPDATE PRODUCT SET PRODUCT_ID = @COUNT:=@COUNT+1;
    //추후에 id 업데이트
    public Product saveProduct(ProductReqDto productReqDto, Principal principal) {
        Optional<Admin> adminOptional = adminRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {
            Product product = new Product(
                    productReqDto.getName(),
                    productReqDto.getPrice(),
                    productReqDto.getQuantity(),
                    productReqDto.getImgPath(),
                    adminOptional.get()
            );
            return productRepository.save(product);
        }
        return null;
    }

    public Product findByProductName(String productName) {
        Optional<Product> product = productRepository.findByName(productName);
        if (product.isPresent()) {
            return product.get();
        }
        return null;
    }

    public Product modifyProduct(int id, ProductReqDto productReqDto) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setName(productReqDto.getName());
            product.setPrice(productReqDto.getPrice());
            product.setQuantity(productReqDto.getQuantity());
            product.setModify_date(LocalDateTime.now());
            product.setImgPath(productReqDto.getImgPath());
            return productRepository.save(product);
        }
        return null;
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    public void initDataProduct(Product product) {
        productRepository.save(product);
    }
}
