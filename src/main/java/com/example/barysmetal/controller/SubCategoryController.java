package com.example.barysmetal.controller;

import com.example.barysmetal.model.Category;
import com.example.barysmetal.model.SubCategory;
import com.example.barysmetal.service.FileStorageService;
import com.example.barysmetal.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/subcategories")
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
}
