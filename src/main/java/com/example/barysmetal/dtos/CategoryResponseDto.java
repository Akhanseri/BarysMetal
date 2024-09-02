package com.example.barysmetal.dtos;

import org.springframework.core.io.Resource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
public class CategoryResponseDto {
    private Long id;
    private String name;
    private String photoPath; // Store file path as String
    private List<SubCategoryDto> subCategories;

    public CategoryResponseDto(Long id, String name, String photoPath, List<SubCategoryDto> subCategories) {
        this.id = id;
        this.name = name;
        this.photoPath = photoPath;
        this.subCategories = subCategories;
    }



    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhotoPath() { return photoPath; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }

    public List<SubCategoryDto> getSubCategories() { return subCategories; }
    public void setSubCategories(List<SubCategoryDto> subCategories) { this.subCategories = subCategories; }}



