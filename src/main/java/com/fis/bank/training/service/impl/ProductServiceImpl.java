package com.fis.bank.training.service.impl;

import com.fis.bank.training.dto.request.ProductRequest;
import com.fis.bank.training.dto.response.ProductResponse;
import com.fis.bank.training.exception.AppException;
import com.fis.bank.training.exception.ErrorCode;
import com.fis.bank.training.mapper.CategoryMapper;
import com.fis.bank.training.mapper.ProductMapper;
import com.fis.bank.training.model.Category;
import com.fis.bank.training.model.Product;
import com.fis.bank.training.repository.CategoryRepository;
import com.fis.bank.training.repository.ProductRepository;
import com.fis.bank.training.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    ProductMapper productMapper;
    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    RuntimeService runtimeService;

    @Override
    public ProductResponse saveProduct(ProductRequest request) {
        if(productRepository.existsProductByName(request.getName()))
            throw new AppException(ErrorCode.USER_EXISTED);
        if (request.getCategory() == null || request.getCategory().getName() == null) {
            request.setCategory(new Category("Default Category"));
        }
        String categoryName = request.getCategory().getName();
        Optional<Category> optionalCategory = categoryRepository.findByName(categoryName);
        Category category = optionalCategory.orElseGet(() -> {
            Category newCategory = new Category(categoryName);
            return categoryRepository.save(newCategory);
        });
        Product product = productMapper.toProduct(request);
        product.setCategory(category);
        productRepository.save(product);
        return productMapper.toProductResponse(product);
    }

    @Override
    public ProductResponse getProductById(String id) {
        return productMapper.toProductResponse(
                productRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED)));
    }

    @Override
    public List<ProductResponse> getProducts() {
        return productRepository.findAll().stream().map(productMapper::toProductResponse).toList();
    }

    @Override
    public void deleteProductById(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductResponse updateProduct(ProductRequest request, String id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        productMapper.updateProduct(product, request);
        if (request.getCategory() != null && request.getCategory().getName() != null) {
            String categoryName = request.getCategory().getName();
            Optional<Category> optionalCategory = categoryRepository.findByName(categoryName);
            Category category = optionalCategory.orElseGet(() -> {
                Category newCategory = new Category(categoryName);
                return categoryRepository.save(newCategory);
            });
            product.setCategory(category);
        }
        productRepository.save(product);
        return productMapper.toProductResponse(product);
    }

    @Override
    public List<ProductResponse> getProductByCategoryId(String category) {
        List<Product> products = productRepository.findByCategoryId(category);

        startCamundaProcess(category);

        return products
                .stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    private void startCamundaProcess(String category) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("categoryId", category);

        runtimeService.startProcessInstanceByKey("productProcess", variables);
    }

    @Override
    public ProductResponse findProductByName(String name) {
        return null;
    }
}
