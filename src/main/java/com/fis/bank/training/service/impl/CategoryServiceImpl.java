package com.fis.bank.training.service.impl;

import com.fis.bank.training.dto.request.CategoryRequest;
import com.fis.bank.training.dto.response.CategoryResponse;
import com.fis.bank.training.exception.AppException;
import com.fis.bank.training.exception.ErrorCode;
import com.fis.bank.training.mapper.CategoryMapper;
import com.fis.bank.training.model.Category;
import com.fis.bank.training.repository.CategoryRepository;
import com.fis.bank.training.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    @Override
    public CategoryResponse saveCategory(CategoryRequest request) {
        if(categoryRepository.existsCategoryByName(request.getName()))
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        Category category = categoryMapper.toCategory(request);
        categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public CategoryResponse getCategoryById(String id) {
        return categoryMapper.toCategoryResponse(
                categoryRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Phân loại không được tìm thấy")));
    }

    @Override
    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::toCategoryResponse).toList();
    }

    @Override
    public CategoryResponse updateCategory(CategoryRequest request, String id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_EXISTED));
        categoryMapper.updateCategory(category, request);
        category = categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);
    }
}
