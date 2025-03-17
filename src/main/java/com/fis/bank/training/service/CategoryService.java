package com.fis.bank.training.service;

import com.fis.bank.training.dto.request.CategoryRequest;
import com.fis.bank.training.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse saveCategory(CategoryRequest request);

    CategoryResponse getCategoryById(String id);

    List<CategoryResponse> getCategories();

    CategoryResponse updateCategory(CategoryRequest request, String id);

    void deleteCategory(String id);
}
