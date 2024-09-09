package com.example.barysmetal.controller;

import com.example.barysmetal.dtos.*;
import com.example.barysmetal.model.Category;
import com.example.barysmetal.repository.CategoryRepository;
import com.example.barysmetal.service.CategoryService;
import com.example.barysmetal.service.FileStorageService;
import com.example.barysmetal.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoryController {
    private final CategoryService categoryService;
    private final FileStorageService fileStorageService;
    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryService categoryService, FileStorageService fileStorageService, ProductService productService, CategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.fileStorageService = fileStorageService;
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }


    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        List<CategoryResponseDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getSubCategoriesOrProducts(@PathVariable Long id) {
//        return categoryService.getSubCategoriesOrProducts(id);
//    }

    @GetMapping("/{categoryId}/")
    public ResponseEntity<CategoryDetailsDto> getCategoryDetails(@PathVariable Long categoryId) {
        CategoryDetailsDto categoryDetails = categoryService.getCategoryDetails(categoryId);
        return ResponseEntity.ok(categoryDetails);
    }

    @GetMapping("/category/{categoryId}/subcategory/{subCategoryId}")
    public ResponseEntity<ProductCategorySubCategoryDto> getProductsByCategoryAndSubCategory(
            @PathVariable Long categoryId,
            @PathVariable Long subCategoryId) {

        ProductCategorySubCategoryDto responseDto = productService.getProductsByCategoryAndSubCategory(categoryId, subCategoryId);

        if (responseDto.getProducts().isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build(); // Возвращает статус 204 No Content
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
