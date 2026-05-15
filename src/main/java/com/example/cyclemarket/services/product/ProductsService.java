package com.example.cyclemarket.services.product;

import com.example.cyclemarket.entities.Category;
import com.example.cyclemarket.entities.Product;
import com.example.cyclemarket.repos.ProductRepo;
import com.example.cyclemarket.services.entity.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductsService {
    private final ProductRepo productRepo;
    private final CategoryService categoryService;

    public List<Product> getProducts(Long categoryId, String sort, Long shopId) {
        Sort sortType = resolveSort(sort);


        if (shopId == null) {
            if (categoryId == null) {
                return productRepo.findAll(sortType);
            }
            Category categoryById = categoryService.getCategoryById(categoryId);
            if (categoryById.getChildren() == null || categoryById.getChildren().isEmpty()) {
                return productRepo.findAllByCategories_Id(categoryId, sortType);
            }
            List<Long> childIds = categoryById.getChildren().stream()
                    .map(Category::getId)
                    .toList();
            return productRepo.findAllByCategories_IdIn(childIds, sortType);

        } else {
            if (categoryId == null) {
                return productRepo.findAllByShopId(shopId, sortType);
            }
            Category categoryById = categoryService.getCategoryById(categoryId);
            if (categoryById.getChildren() == null || categoryById.getChildren().isEmpty()) {
                return productRepo.findAllByShopIdAndCategoryId(shopId, categoryId, sortType);
            }
            List<Long> childIds = categoryById.getChildren().stream()
                    .map(Category::getId)
                    .toList();
            return productRepo.findAllByShopIdAndCategoryIdIn(shopId, childIds, sortType);
        }
    }

    private Sort resolveSort(String sort) {
        if ("priceAsc".equals(sort)) {
            return Sort.by("productPrice").ascending();
        }
        if ("priceDesc".equals(sort)) {
            return Sort.by("productPrice").descending();
        }
        return Sort.unsorted();
    }
}
