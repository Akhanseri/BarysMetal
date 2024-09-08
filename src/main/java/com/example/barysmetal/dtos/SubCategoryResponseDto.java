package com.example.barysmetal.dtos;


public class SubCategoryResponseDto {
    private Long id;
    private String name;
    private String photoPath;  // URL для загрузки фото

    public SubCategoryResponseDto(Long id, String name, String photoPath, String photoUrl) {
        this.id = id;
        this.name = name;
        this.photoPath = photoUrl;
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


    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
