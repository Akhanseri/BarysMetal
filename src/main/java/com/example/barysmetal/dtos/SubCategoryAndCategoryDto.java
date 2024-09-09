package com.example.barysmetal.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubCategoryAndCategoryDto {
    private Long id;
    private String name;
    private String photoPath;

    // Add category information
    private Long categoryId;
    private String categoryName;
}
