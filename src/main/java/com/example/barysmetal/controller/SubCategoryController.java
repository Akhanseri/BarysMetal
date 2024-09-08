package com.example.barysmetal.controller;

import com.example.barysmetal.model.Category;
import com.example.barysmetal.model.SubCategory;
import com.example.barysmetal.service.FileStorageService;
import com.example.barysmetal.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subcategories")
@CrossOrigin(origins = "http://localhost:5173")
public class SubCategoryController {
    @Autowired
    private SubCategoryService subCategoryService;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SubCategory> createSubCategory(
            @RequestParam("name") String name,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("file") MultipartFile file) {

        String filePath = fileStorageService.storeFile(file);
        SubCategory subCategory = new SubCategory();
        subCategory.setName(name);
        subCategory.setCategory(new Category(categoryId)); // Используем конструктор для установки ID категории
        subCategory.setPhoto(filePath);


        SubCategory savedSubCategory = subCategoryService.saveSubCategory(subCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSubCategory);
    }

    @DeleteMapping("/{subCategoryId}")
    public ResponseEntity<Void> deleteSubCategory(@PathVariable Long subCategoryId) {
        subCategoryService.deleteSubCategory(subCategoryId);
        return ResponseEntity.noContent().build();  // Возвращает статус 204 No Content
    }

    @GetMapping
    public List<SubCategory> getAllSubCategories() {
        return subCategoryService.getAllSubCategories();
    }
}
