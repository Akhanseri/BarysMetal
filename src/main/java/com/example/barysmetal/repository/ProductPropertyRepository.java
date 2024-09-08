package com.example.barysmetal.repository;

import com.example.barysmetal.model.Product;
import com.example.barysmetal.model.ProductProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductPropertyRepository extends JpaRepository<ProductProperty,Long> {
    List<ProductProperty> findByProduct(Product product);

    void deleteByProductId(Long productId);
}

