package com.fis.bank.training.service;

import com.fis.bank.training.dto.request.ProductRequest;
import com.fis.bank.training.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse saveProduct(ProductRequest request);

    ProductResponse getProductById(String id);

    List<ProductResponse> getProducts();

    void deleteProductById(String id);

    ProductResponse updateProduct(ProductRequest request, String id);

    List<ProductResponse> getProductByCategoryId(String id);

    ProductResponse findProductByName(String name);
}
