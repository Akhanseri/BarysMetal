package com.example.barysmetal.dtos;

import com.example.barysmetal.model.Product;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class ProductCategorySubCategoryDto {
    private Long categoryId;
    private String categoryName;
    private Long subCategoryId;
    private String subCategoryName;
    private List<ProductDto> products;
}
