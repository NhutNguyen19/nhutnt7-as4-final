package com.fis.bank.training.controller;

import com.fis.bank.training.dto.ApiResponse;
import com.fis.bank.training.dto.request.ProductRequest;
import com.fis.bank.training.dto.response.ProductResponse;
import com.fis.bank.training.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductController {

    ProductService productService;

    @PostMapping("/save")
    ApiResponse<ProductResponse> createProduct(@RequestBody ProductRequest request){
        return ApiResponse.<ProductResponse>builder()
                .data(productService.saveProduct(request))
                .build();
    }

    @GetMapping("/{id}/product")
    ApiResponse<ProductResponse> getProductById(@PathVariable("id") String id){
        return ApiResponse.<ProductResponse>builder()
                .data(productService.getProductById(id))
                .build();
    }

    @GetMapping
    ApiResponse<List<ProductResponse>> getProducts(){
        return ApiResponse.<List<ProductResponse>>builder()
                .data(productService.getProducts())
                .build();
    }

    @DeleteMapping("/{id}/delete")
    ApiResponse<String> deleteProduct(@PathVariable String id){
        productService.deleteProductById(id);
        return ApiResponse.<String>builder()
                .build();
    }

    @PutMapping("/{id}/update")
    ApiResponse<ProductResponse> updateProduct(@RequestBody ProductRequest request,@PathVariable String id){
        return ApiResponse.<ProductResponse>builder()
                .data(productService.updateProduct(request, id))
                .build();
    }

    @GetMapping("/{id}/category")
    ApiResponse<List<ProductResponse>> getProductByCategoryId(@PathVariable String id){
        return ApiResponse.<List<ProductResponse>>builder()
                .data(productService.getProductByCategoryId(id))
                .build();
    }

}
