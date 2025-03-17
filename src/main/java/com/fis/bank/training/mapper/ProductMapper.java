package com.fis.bank.training.mapper;

import com.fis.bank.training.dto.request.ProductRequest;
import com.fis.bank.training.dto.response.ProductResponse;
import com.fis.bank.training.model.Product;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductRequest request);
    ProductResponse toProductResponse(Product product);
}
