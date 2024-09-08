package com.example.barysmetal.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class SubCategoryDto {
    public SubCategoryDto(Long id, String name, String photoPath) {
        this.id = id;
        this.name = name;
        this.photoPath = photoPath;
    }

    Long id;
    String name;
    String photoPath;

    public SubCategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}