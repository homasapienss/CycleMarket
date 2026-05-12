package com.example.cyclemarket.services;

import com.example.cyclemarket.entities.Category;
import com.example.cyclemarket.exception.notfound.CategoryNotFoundException;
import com.example.cyclemarket.repos.CategoryRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepo categoryRepo;

    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }
    public Category getCategoryById(Long categoryId) {
        return categoryRepo.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
    }

    @Transactional
    public List<Category> getAllParentCategories() {
        return categoryRepo.findAllByParentIsNull();
    }

    @Transactional
    public List<Category> getWeakCategories() {
        return categoryRepo.findAllByParentIsNotNull();
    }
}
