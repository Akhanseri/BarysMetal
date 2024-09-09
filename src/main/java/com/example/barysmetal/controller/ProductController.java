package com.example.barysmetal.controller;

import com.example.barysmetal.repository.ProductRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.example.barysmetal.dtos.ProductPropertyDto;
import com.example.barysmetal.model.Category;
import com.example.barysmetal.model.Product;
import com.example.barysmetal.model.ProductProperty;
import com.example.barysmetal.model.SubCategory;
import com.example.barysmetal.service.FileStorageService;
import com.example.barysmetal.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;
    private final FileStorageService fileStorageService;
    private final ObjectMapper objectMapper; // Jackson ObjectMapper for JSON parsing

    @Autowired
    public ProductController(ProductService productService, ProductRepository productRepository, FileStorageService fileStorageService, ObjectMapper objectMapper) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.fileStorageService = fileStorageService;
        this.objectMapper = objectMapper;
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();  // Возвращает статус 204 No Content
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> createProduct(
            @RequestParam("name") String name,
            @RequestParam("price") int price,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam(value = "subCategoryId", required = false) Long subCategoryId,
            @RequestParam("description") String description,
            @RequestParam("kaspi") String kaspi,
            @RequestParam("file") MultipartFile file,
            @RequestParam("productProperties") String productPropertiesJson) { // JSON string
        productPropertiesJson = productPropertiesJson.substring(1, productPropertiesJson.length() - 1);


        String filePath = fileStorageService.storeFile(file);

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setKaspi(kaspi);
        product.setCategory(new Category(categoryId));
        if (subCategoryId != null) {
            product.setSubCategory(new SubCategory(subCategoryId));
        }
        product.setPhotoPath(filePath);




        // Convert JSON string to List<ProductPropertyDto>
        List<ProductPropertyDto> productProperties;
        try {
            productProperties = objectMapper.readValue(productPropertiesJson, new TypeReference<List<ProductPropertyDto>>() {});
        } catch (IOException e) {
            return ResponseEntity.badRequest().build(); // Bad request if JSON is invalid
        }

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