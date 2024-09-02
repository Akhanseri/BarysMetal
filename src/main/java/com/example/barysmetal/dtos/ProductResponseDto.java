package com.example.barysmetal.dtos;

import org.springframework.core.io.Resource;


public class ProductResponseDto {
    private Long id;
    private String name;
    private String photoPath; // Путь к фото, хранимый в базе данных
    private String photoUrl;  // URL для загрузки фото
    private int price;

    public ProductResponseDto(Long id, String name, String photoPath, String photoUrl, int price) {
        this.id = id;
        this.name = name;
        this.photoPath = photoPath;
        this.photoUrl = photoUrl;
        this.price = price;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
