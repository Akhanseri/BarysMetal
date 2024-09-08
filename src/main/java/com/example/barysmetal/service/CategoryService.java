package com.example.barysmetal.service;

import com.example.barysmetal.dtos.*;
import com.example.barysmetal.model.Category;
import com.example.barysmetal.model.Product;
import com.example.barysmetal.model.ProductProperty;
import com.example.barysmetal.model.SubCategory;
import com.example.barysmetal.repository.CategoryRepository;
import com.example.barysmetal.repository.ProductPropertyRepository;
import com.example.barysmetal.repository.ProductRepository;
import com.example.barysmetal.repository.SubCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;
    private SubCategoryRepository subCategoryRepository;
    private FileStorageService fileStorageService;
    private ProductPropertyRepository productPropertyRepository;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository, SubCategoryRepository subCategoryRepository, FileStorageService fileStorageService, ProductPropertyRepository productPropertyRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.fileStorageService = fileStorageService;
        this.productPropertyRepository = productPropertyRepository;
    }

    public void deleteCategory(Long categoryId) {
        // Проверяем, существует ли категория
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        // Логика удаления (удаление связи с продуктами и подкатегориями)
        categoryRepository.delete(category);  // Удаляем категорию
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

    public CategoryDetailsDto getCategoryDetails(Long categoryId) {
        // Retrieve the category
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        // Get subcategories
        List<SubCategory> subCategories = category.getSubCategories();

        if (!subCategories.isEmpty()) {
            // If there are subcategories, return subcategory details
            List<SubCategoryDto> subCategoryDtos = subCategories.stream()
                    .map(subCategory -> SubCategoryDto.builder()
                            .id(subCategory.getId())
                            .name(subCategory.getName())
                            .photoPath(subCategory.getPhoto())
                            .build())
                    .collect(Collectors.toList());

            return CategoryDetailsDto.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .photoPath(category.getPhoto())
                    .subCategories(subCategoryDtos)  // Return subcategories
                    .products(Collections.emptyList())  // Return empty product list
                    .build();
        } else {
            // If there are no subcategories, return products directly under the category
            List<Product> productsWithoutSubCategory = productRepository.findByCategoryAndSubCategoryIsNull(category);

            List<ProductDto> productDtos = productsWithoutSubCategory.stream()
                    .map(product -> {
                        // Retrieve product properties
                        List<ProductProperty> properties = productPropertyRepository.findByProduct(product);

                        // Map product properties to ProductPropertyDto
                        List<ProductPropertyDto> propertyDtos = properties.stream()
                                .map(property -> ProductPropertyDto.builder()
                                        .key(property.getKey())
                                        .value(property.getValue())
                                        .build())
                                .collect(Collectors.toList());

                        return ProductDto.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .price(product.getPrice())
                                .description(product.getDescription())
                                .photoPath(product.getPhotoPath())
                                .kaspi(product.getKaspi())
                                .properties(propertyDtos)  // Add product properties
                                .build();
                    })
                    .collect(Collectors.toList());

            return CategoryDetailsDto.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .photoPath(category.getPhoto())
                    .subCategories(Collections.emptyList())  // Return empty subcategory list
                    .products(productDtos)  // Return products with properties if available
                    .build();
        }
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

