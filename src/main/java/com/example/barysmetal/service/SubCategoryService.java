package com.example.barysmetal.service;

import com.example.barysmetal.model.SubCategory;
import com.example.barysmetal.repository.SubCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubCategoryService {
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    public SubCategoryService(SubCategoryRepository subCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
    }

    public List<SubCategory> getAllSubCategories() {
        return subCategoryRepository.findAll();
    }

    public void deleteSubCategory(Long subCategoryId) {
        // Проверяем, существует ли подкатегория
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new EntityNotFoundException("SubCategory not found"));

        // Логика удаления (удаление связи с продуктами)
        subCategoryRepository.delete(subCategory);  // Удаляем подкатегорию
    }

    public SubCategory saveSubCategory(SubCategory subCategory) {
        return subCategoryRepository.save(subCategory);
    }
}
