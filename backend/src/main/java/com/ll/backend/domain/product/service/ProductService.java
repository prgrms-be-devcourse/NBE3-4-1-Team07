package com.ll.backend.domain.product.service;

import com.ll.backend.domain.product.dto.ProductRequestDto;
import com.ll.backend.domain.product.entity.Product;
import com.ll.backend.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

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

        Product product = new Product(
                productName,
                price,
                quantity,
                imagePath
        );

        return productRepository.save(product);
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
    public Product saveProduct(ProductRequestDto productRequestDto) {
        Product product = new Product(
                productRequestDto.getName(),
                productRequestDto.getPrice(),
                productRequestDto.getQuantity(),
                productRequestDto.getImgPath()
        );
        return productRepository.save(product);
    }

    public Product findByProductName(String productName) {
        Optional<Product> product = productRepository.findByName(productName);
        if (product.isPresent()) {
            return product.get();
        }
        return null;
    }
}
