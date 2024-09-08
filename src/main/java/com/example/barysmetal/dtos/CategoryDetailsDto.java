package com.example.barysmetal.dtos;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDetailsDto {
    private Long id;
    private String name;
    private String photoPath;

    private List<SubCategoryDto> subCategories = new ArrayList<>(); // List of subcategories
    private List<ProductDto> products = new ArrayList<>();          // List of direct products
}


