package com.fis.bank.training.mapper;

import com.fis.bank.training.dto.request.CategoryRequest;
import com.fis.bank.training.dto.response.CategoryResponse;
import com.fis.bank.training.dto.response.ProductResponse;
import com.fis.bank.training.model.Category;
import com.fis.bank.training.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toCategory(CategoryRequest request);

    default CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .products(category.getProducts().stream()
                        .map(this::toProductResponse)
                        .collect(Collectors.toList()))
                .build();
    }
    private ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .build();
    }


    void updateCategory(@MappingTarget Category category, CategoryRequest request);
}
