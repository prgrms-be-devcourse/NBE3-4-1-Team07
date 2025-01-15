package com.ll.backend.domain.product.service;

import com.ll.backend.domain.product.entity.Product;
import com.ll.backend.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public long count() {
        return productRepository.count();
    }

    public Product join(String productName, int price, int quantity, String imagePath) {
        productRepository
                .findByName(productName)
                .ifPresent(_ -> {
                    throw new ServiceException("409-1 해당 product_name은 이미 사용중입니다.");
                });

        Product product = new Product();
        product.setName(productName);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setCreated_date(LocalDateTime.now());
        product.setModify_date(LocalDateTime.now());
        product.setImagePath(imagePath);

        return productRepository.save(product);
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }
}
