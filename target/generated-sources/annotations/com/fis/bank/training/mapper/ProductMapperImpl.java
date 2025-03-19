package com.fis.bank.training.mapper;

import com.fis.bank.training.dto.request.ProductRequest;
import com.fis.bank.training.dto.response.ProductResponse;
import com.fis.bank.training.model.Product;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Eclipse Adoptium)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toProduct(ProductRequest request) {
        if ( request == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.name( request.getName() );
        product.description( request.getDescription() );
        product.price( request.getPrice() );
        product.stockQuantity( request.getStockQuantity() );
        product.category( request.getCategory() );

        return product.build();
    }

    @Override
    public ProductResponse toProductResponse(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductResponse.ProductResponseBuilder productResponse = ProductResponse.builder();

        productResponse.id( product.getId() );
        productResponse.name( product.getName() );
        productResponse.description( product.getDescription() );
        productResponse.price( product.getPrice() );
        productResponse.stockQuantity( product.getStockQuantity() );
        productResponse.category( product.getCategory() );

        return productResponse.build();
    }

    @Override
    public void updateProduct(Product product, ProductRequest request) {
        if ( request == null ) {
            return;
        }

        product.setName( request.getName() );
        product.setDescription( request.getDescription() );
        product.setPrice( request.getPrice() );
        product.setStockQuantity( request.getStockQuantity() );
        product.setCategory( request.getCategory() );
    }
}
