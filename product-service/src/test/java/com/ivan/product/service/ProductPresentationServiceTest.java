package com.ivan.product.service;

import com.ivan.product.dto.ProductRequest;
import com.ivan.product.dto.ProductResponse;
import com.ivan.product.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ProductPresentationServiceTest {

    private ProductPresentationService underTest;

    @BeforeEach
    void setUp() {
        underTest = new ProductPresentationService();
    }

    @Test
    void convertToModel_shouldReturnProduct() {
        // given
        ProductRequest productRequest = ProductRequest.builder()
                .name("test")
                .description("test")
                .price(10.5F)
                .build();

        // when
        Product result = underTest.convertToModel(productRequest);

        // then
        assertThat(result.getName()).isEqualTo(productRequest.getName());
        assertThat(result.getDescription()).isEqualTo(productRequest.getDescription());
        assertThat(result.getPrice()).isEqualTo(productRequest.getPrice());
    }


    @Test
    void convertToDto_shouldReturnProductResponse() {
        // given
        Product product = Product.builder()
                .name("test")
                .description("test")
                .price(10.5F)
                .build();

        // when
        ProductResponse result = underTest.convertToDto(product);

        // then
        assertThat(result.getName()).isEqualTo(product.getName());
        assertThat(result.getDescription()).isEqualTo(product.getDescription());
        assertThat(result.getPrice()).isEqualTo(product.getPrice());
    }

    @Test
    void convertToDtoList_shouldReturnProductResponseList() {
        // given
        List<Product> products = List.of(
                Product.builder().id(1).name("test1").description("test1").price(10.5F).build(),
                Product.builder().id(2).name("test2").description("test2").price(20.5F).build()
        );

        // when
        List<ProductResponse> result = underTest.convertToDtoList(products);

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo(products.get(0).getId());
        assertThat(result.get(0).getName()).isEqualTo(products.get(0).getName());
        assertThat(result.get(0).getDescription()).isEqualTo(products.get(0).getDescription());
        assertThat(result.get(0).getPrice()).isEqualTo(products.get(0).getPrice());
        assertThat(result.get(1).getId()).isEqualTo(products.get(1).getId());
        assertThat(result.get(1).getName()).isEqualTo(products.get(1).getName());
        assertThat(result.get(1).getDescription()).isEqualTo(products.get(1).getDescription());
        assertThat(result.get(1).getPrice()).isEqualTo(products.get(1).getPrice());
    }
}