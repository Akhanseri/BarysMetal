package com.example.barysmetal.dtos;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {
    private Long id;
    private String name;
    private int price;
    private String description;
    private String kaspi;
    private String photoPath;
    private CategoryDto category;
    private SubCategoryDto subCategory;
    private List<ProductPropertyDto> properties = new ArrayList<>();

}
