package com.fis.bank.training.controller;

import com.fis.bank.training.dto.ApiResponse;
import com.fis.bank.training.dto.request.CategoryRequest;
import com.fis.bank.training.dto.response.CategoryResponse;
import com.fis.bank.training.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryController {
    CategoryService categoryService;

    @PostMapping("/save")
    ApiResponse<CategoryResponse> createCategory(@RequestBody CategoryRequest request){
        return ApiResponse.<CategoryResponse>builder()
                .data(categoryService.saveCategory(request))
                .build();
    }

    @GetMapping("/{id}/category")
    ApiResponse<CategoryResponse> getCategoryById(@PathVariable("id") String id){
        return ApiResponse.<CategoryResponse>builder()
                .data(categoryService.getCategoryById(id))
                .build();
    }

    @GetMapping
    ApiResponse<List<CategoryResponse>> getCategories(){
        return ApiResponse.<List<CategoryResponse>>builder()
                .data(categoryService.getCategories())
                .build();
    }

    @PutMapping("/{id}/update")
    ApiResponse<CategoryResponse> updateCategory(@RequestBody CategoryRequest request,@PathVariable("id") String id){
        return ApiResponse.<CategoryResponse>builder()
                .data(categoryService.updateCategory(request, id))
                .build();
    }

    @DeleteMapping("/{id}/delete")
    ApiResponse<String> deleteCategory(@PathVariable String id){
        categoryService.deleteCategory(id);
        return ApiResponse.<String>builder()
                .build();
    }
}
