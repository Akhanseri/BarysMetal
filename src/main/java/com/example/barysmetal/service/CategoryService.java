package com.example.barysmetal.service;

import com.example.barysmetal.dtos.CategoryResponseDto;
import com.example.barysmetal.dtos.SubCategoryDto;
import com.example.barysmetal.model.Category;
import com.example.barysmetal.model.Product;
import com.example.barysmetal.model.SubCategory;
import com.example.barysmetal.repository.CategoryRepository;
import com.example.barysmetal.repository.ProductRepository;
import com.example.barysmetal.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;
    private SubCategoryRepository subCategoryRepository;
    private FileStorageService fileStorageService;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository, SubCategoryRepository subCategoryRepository, FileStorageService fileStorageService) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.fileStorageService = fileStorageService;
    }






    public List<CategoryResponseDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(category -> {
            List<SubCategoryDto> subCategoryDtos = category.getSubCategories().stream()
                    .map(subCategory -> new SubCategoryDto(subCategory.getId(), subCategory.getName(),subCategory.getPhoto()))
                    .collect(Collectors.toList());

            // Create DTO with category properties
            return new CategoryResponseDto(
                    category.getId(),
                    category.getName(),
                    category.getPhoto(), // Assuming this is a path or identifier, not the actual file
                    subCategoryDtos
            );
        }).collect(Collectors.toList());
    }




    public ResponseEntity<?> getSubCategoriesOrProducts(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }

        List<SubCategory> subCategories = subCategoryRepository.findByCategoryId(categoryId);
        if (!subCategories.isEmpty()) {
            // Map to DTOs to avoid infinite recursion
            List<SubCategoryDto> subCategoryDtos = subCategories.stream()
                    .map(subCategory -> new SubCategoryDto(subCategory.getId(), subCategory.getName(), subCategory.getPhoto()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(subCategoryDtos);
        } else {
            List<Product> products = productRepository.findByCategoryId(categoryId);
            return ResponseEntity.ok(products);
        }
    }



    public ResponseEntity<List<Product>> getProductsBySubCategory(Long categoryId, Long subCategoryId) {
        List<Product> products = productRepository.findByCategoryIdAndSubCategoryId(categoryId, subCategoryId);
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }




}

