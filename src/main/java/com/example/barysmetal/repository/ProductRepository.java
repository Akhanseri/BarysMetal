package com.example.barysmetal.repository;
import com.example.barysmetal.model.Category;
import com.example.barysmetal.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByCategoryIdAndSubCategoryId(Long categoryId, Long subCategoryId);

    List<Product> findByCategoryAndSubCategoryIsNull(Category category);
}

