package com.example.cyclemarket.services.product;

import com.example.cyclemarket.dto.ProductCreateRequest;
import com.example.cyclemarket.entities.Category;
import com.example.cyclemarket.entities.Manufacturer;
import com.example.cyclemarket.entities.Product;
import com.example.cyclemarket.entities.ProductImage;
import com.example.cyclemarket.exception.notfound.ProductNotFoundException;
import com.example.cyclemarket.repos.ProductRepo;
import com.example.cyclemarket.services.entity.CategoryService;
import com.example.cyclemarket.services.entity.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductManagementService {
    private final ProductRepo productRepo;
    private final ManufacturerService manufacturerService;
    private final CategoryService categoryService;

    @Value("${spring.images.dir}")
    private String imageDir;

    @Transactional
    public Product createProduct(ProductCreateRequest request) throws IOException {
        Manufacturer manufacturer = manufacturerService.getById(request.getManufacturerId());
        Category category = categoryService.getCategoryById(request.getCategoryId());

        Product product = new Product();
        product.setProductName(request.getProductName());
        product.setProductDescription(request.getProductDescription());
        product.setProductPrice(request.getProductPrice());
        product.setManufacturer(manufacturer);
        product.getCategories().add(category);

        Product savedProduct = productRepo.save(product);

        if (request.getImage() != null && !request.getImage().isEmpty()) {
            String imageUrl = saveImageFile(request.getImage().getOriginalFilename(), request.getImage().getBytes());

            ProductImage productImage = new ProductImage();
            productImage.setProduct(savedProduct);
            productImage.setImageUrl(imageUrl);
            productImage.setMain(true);

            savedProduct.getProductImages().add(productImage);
        }

        return productRepo.save(savedProduct);
    }
    private String saveImageFile(String originalFilename, byte[] bytes) throws IOException {
        String extension = StringUtils.getFilenameExtension(originalFilename);
        String safeExtension = extension == null ? "jpg" : extension.toLowerCase();

        String generatedFileName = UUID.randomUUID() + "." + safeExtension;

        Path uploadDir = Paths.get(imageDir, "products");
        Files.createDirectories(uploadDir);

        Path targetFile = uploadDir.resolve(generatedFileName);
        Files.write(targetFile, bytes);

        return "/products/" + generatedFileName;
    }

    @Transactional
    public Product getProductById(Long id) {
        return productRepo.findById(id).orElseThrow(ProductNotFoundException::new);
    }
}
