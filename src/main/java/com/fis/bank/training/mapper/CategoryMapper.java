package com.fis.bank.training.mapper;

import com.fis.bank.training.dto.request.CategoryRequest;
import com.fis.bank.training.dto.response.CategoryResponse;
import com.fis.bank.training.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toCategory(CategoryRequest request);

    CategoryResponse toCategoryResponse(Category category);

    void updateCategory(@MappingTarget Category category, CategoryRequest request);
}
