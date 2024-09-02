package com.example.barysmetal.dtos;

import org.springframework.core.io.Resource;


public class SubCategoryResponseDto {
    private Long id;
    private String name;
    private String photoUrl;  // URL для загрузки фото

    public SubCategoryResponseDto(Long id, String name, String photoPath, String photoUrl) {
        this.id = id;
        this.name = name;
        this.photoUrl = photoUrl;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
