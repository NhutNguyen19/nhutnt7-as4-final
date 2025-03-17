package com.fis.bank.training.service.impl;

import com.fis.bank.training.dto.request.ProductRequest;
import com.fis.bank.training.dto.response.ProductResponse;
import com.fis.bank.training.mapper.ProductMapper;
import com.fis.bank.training.repository.CategoryRepository;
import com.fis.bank.training.repository.ProductRepository;
import com.fis.bank.training.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    ProductMapper productMapper;
    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    @Override
    public ProductResponse saveProduct(ProductRequest request) {
        return null;
    }

    @Override
    public ProductResponse getProductById(String id) {
        return null;
    }

    @Override
    public List<ProductResponse> getProducts() {
        return List.of();
    }

    @Override
    public void deleteProductById(String id) {

    }

    @Override
    public ProductResponse updateProduct(ProductRequest request, String id) {
        return null;
    }

    @Override
    public List<ProductResponse> getProductByCategory(String category) {
        return List.of();
    }

    @Override
    public ProductResponse findProductByName(String name) {
        return null;
    }
}
