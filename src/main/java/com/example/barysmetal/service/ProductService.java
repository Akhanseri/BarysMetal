package com.example.barysmetal.service;

import com.example.barysmetal.model.Product;
import com.example.barysmetal.repository.ProductPropertyRepository;
import com.example.barysmetal.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductPropertyRepository productPropertyRepository;

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
