package com.example.barysmetal.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.font.TextHitInfo;

@Getter
@Setter
@NoArgsConstructor
public class SubCategoryDto {
    public SubCategoryDto(Long id, String name, String photo) {
        this.id = id;
        this.name = name;
        this.photo = photo;
    }

    Long id;
    String name;
    String photo;

    public SubCategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}