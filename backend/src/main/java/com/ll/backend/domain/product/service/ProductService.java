package com.ll.backend.domain.product.service;

import com.ll.backend.domain.admin.entity.Admin;
import com.ll.backend.domain.admin.repository.AdminRepository;
import com.ll.backend.domain.product.dto.ProductReqDto;
import com.ll.backend.domain.product.entity.Product;
import com.ll.backend.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;

    public long count() {
        return productRepository.count();
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }


    public String saveImage(MultipartFile image) {
        try {
            // 이미지 저장 경로 설정 (프로젝트 루트의 uploads 디렉토리)
            String uploadDir = "src/main/resources/static/uploads";

            String originalFilename = image.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

            String uniqueFileName = StringUtils.cleanPath(UUID.randomUUID().toString()) +extension;
//            String uniqueFileName = UUID.randomUUID().toString() + extension;

            // 디렉토리가 없으면 생성
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 이미지 저장
            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // 데이터베이스에 저장할 이미지 경로
            return "/uploads/" + uniqueFileName;

        } catch (IOException e) {
            throw new RuntimeException("이미지 저장에 실패했습니다.", e);
        }

    }

    //ALTER TABLE PRODUCT AUTO_INCREMENT=1;
    //SET @COUNT = 0;
    //UPDATE PRODUCT SET PRODUCT_ID = @COUNT:=@COUNT+1;
    //추후에 id 업데이트
    public Product saveProduct(ProductReqDto productReqDto, String imgPath, Principal principal) {
        Optional<Admin> adminOptional = adminRepository.findByUsername(principal.getName());
        if (adminOptional.isPresent()) {
            Product product = new Product(
                    productReqDto.getName(),
                    productReqDto.getPrice(),
                    productReqDto.getQuantity(),
                    imgPath,
                    adminOptional.get()
            );
            return productRepository.save(product);
        }
        return null;
    }

    public Product modifyProduct(int id, ProductReqDto productReqDto, String imgPath) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setName(productReqDto.getName());
            product.setPrice(productReqDto.getPrice());
            product.setQuantity(productReqDto.getQuantity());
            product.setModify_date(LocalDateTime.now());
            product.setImgPath(imgPath);
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
