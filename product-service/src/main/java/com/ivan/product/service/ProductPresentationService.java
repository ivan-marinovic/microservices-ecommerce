package com.ivan.product.service;

import com.ivan.product.dto.ProductRequest;
import com.ivan.product.dto.ProductResponse;
import com.ivan.product.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductPresentationService {

    public Product convertToModel(ProductRequest productRequest) {
        return Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
    }

    public ProductResponse convertToDto(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    public List<ProductResponse> convertToDtoList(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }
}
