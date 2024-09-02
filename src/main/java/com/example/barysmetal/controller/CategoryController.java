package com.example.barysmetal.controller;

import com.example.barysmetal.dtos.CategoryResponseDto;
import com.example.barysmetal.dtos.ProductResponseDto;
import com.example.barysmetal.dtos.SubCategoryDto;
import com.example.barysmetal.dtos.SubCategoryResponseDto;
import com.example.barysmetal.model.Category;
import com.example.barysmetal.model.Product;
import com.example.barysmetal.repository.CategoryRepository;
import com.example.barysmetal.service.CategoryService;
import com.example.barysmetal.service.FileStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final FileStorageService fileStorageService;
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryService categoryService, FileStorageService fileStorageService, CategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.fileStorageService = fileStorageService;
        this.categoryRepository = categoryRepository;
    }


    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        List<CategoryResponseDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getSubCategoriesOrProducts(@PathVariable Long id) {
        return categoryService.getSubCategoriesOrProducts(id);
    }
    @GetMapping("/{categoryId}/sub/{subCategoryId}/products")
    public ResponseEntity<List<Product>> getProductsBySubCategory(@PathVariable Long categoryId, @PathVariable Long subCategoryId) {
        return categoryService.getProductsBySubCategory(categoryId, subCategoryId);
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Category> createCategory(
            @RequestParam("name") String name,
            @RequestParam("file") MultipartFile file) {

        String filePath = fileStorageService.storeFile(file);
        Category category = new Category();
        category.setName(name);
        category.setPhoto(filePath);

        Category savedCategory = categoryService.saveCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }
}
