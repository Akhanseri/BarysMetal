package com.example.barysmetal.controller;

import com.example.barysmetal.dtos.ProductPropertyDto;
import com.example.barysmetal.model.Category;
import com.example.barysmetal.model.Product;
import com.example.barysmetal.model.ProductProperty;
import com.example.barysmetal.model.SubCategory;
import com.example.barysmetal.service.FileStorageService;
import com.example.barysmetal.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> createProduct(
            @RequestParam("name") String name,
            @RequestParam("price") int price,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("subCategoryId") Long subCategoryId,
            @RequestParam("description") String description,
            @RequestParam("kaspi") String kaspi,
            @RequestParam("file") MultipartFile file,
            @RequestParam("productProperties") List<ProductPropertyDto> productProperties) {

        String filePath = fileStorageService.storeFile(file);

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setKaspi(kaspi);
        product.setCategory(new Category(categoryId)); // Set category by ID
        product.setSubCategory(new SubCategory(subCategoryId)); // Set subcategory by ID
        product.setPhotoPath(filePath);

        // Add product properties
        List<ProductProperty> properties = productProperties.stream().map(dto -> {
            ProductProperty property = new ProductProperty();
            property.setKey(dto.getKey());
            property.setValue(dto.getValue());
            property.setProduct(product);
            return property;
        }).collect(Collectors.toList());

        product.setProductProperties(properties);

        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

}
