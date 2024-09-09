package com.example.barysmetal.service;

import com.example.barysmetal.dtos.ProductCategorySubCategoryDto;
import com.example.barysmetal.dtos.ProductDto;
import com.example.barysmetal.dtos.ProductPropertyDto;
import com.example.barysmetal.model.Category;
import com.example.barysmetal.model.Product;
import com.example.barysmetal.model.SubCategory;
import com.example.barysmetal.repository.CategoryRepository;
import com.example.barysmetal.repository.ProductPropertyRepository;
import com.example.barysmetal.repository.ProductRepository;
import com.example.barysmetal.repository.SubCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductPropertyRepository productPropertyRepository;
    @Autowired
    private SubCategoryRepository subCategoryRepository;

    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
    }

    public ProductCategorySubCategoryDto getProductsByCategoryAndSubCategory(Long categoryId, Long subCategoryId) {
        // Fetch the category
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        // Fetch the subcategory
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new EntityNotFoundException("SubCategory not found"));

        // Fetch the products by category and subcategory
        List<Product> products = productRepository.findByCategoryIdAndSubCategoryId(categoryId, subCategoryId);

        // Map products to ProductDto
        List<ProductDto> productDtos = products.stream()
                .map(this::mapToProductDto)  // Reuse the mapping method for products
                .collect(Collectors.toList());

        // Build the response DTO
        return ProductCategorySubCategoryDto.builder()
                .categoryId(category.getId())
                .categoryName(category.getName())
                .subCategoryId(subCategory.getId())
                .subCategoryName(subCategory.getName())
                .products(productDtos)
                .build();
    }

    private ProductDto mapToProductDto(Product product) {
        List<ProductPropertyDto> propertyDtos = product.getProductProperties().stream()
                .map(property -> ProductPropertyDto.builder()
                        .id(property.getId())
                        .key(property.getKey())
                        .value(property.getValue())
                        .build())
                .collect(Collectors.toList());

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .kaspi(product.getKaspi())
                .photoPath(product.getPhotoPath())
                .properties(propertyDtos)
                .build();
    }



    public void deleteProduct(Long productId) {
        // Проверяем, существует ли продукт
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        // Удаляем связанные свойства продукта, если они есть
        productPropertyRepository.deleteByProductId(productId);

        // Удаляем сам продукт
        productRepository.delete(product);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}
