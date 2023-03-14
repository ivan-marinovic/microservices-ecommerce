package com.ivan.product.service;

import com.ivan.product.exception.ProductDoesNotExistsException;
import com.ivan.product.model.Product;
import com.ivan.product.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    private ProductService underTest;
    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        underTest = new ProductService(productRepository);
    }

    @Test
    void createProduct_shouldCreateProduct() {
        // given
        Product product = Product.builder()
                .id(1)
                .name("test")
                        .description("test")
                                .price(10.5F)
                                        .build();

        // when
        underTest.createProduct(product);
        // then
        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(productArgumentCaptor.capture());
        Product capturedProduct = productArgumentCaptor.getValue();
        assertThat(capturedProduct).isEqualTo(product);
    }

    @Test
    void getAllProducts_shouldReturnAllProducts() {
        // when
        underTest.getAllProducts();
        // then
        verify(productRepository).findAll();
    }

    @Test
    void deleteProduct_shouldDeleteProduct() throws ProductDoesNotExistsException {
        // given
        int productId = 1;
        Product product = Product.builder()
                .id(productId)
                .name("test")
                .description("test")
                .price(10.5F)
                .build();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // when
        underTest.deleteProduct(productId);

        // then
        verify(productRepository).findById(productId);
        verify(productRepository).deleteById(productId);
    }

    @Test
    void deleteProduct_whenProductDoesNotExist_shouldThrowProductDoesNotExistsException() {
        // given
        int productId = 1;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // when
        Assertions.assertThrows(ProductDoesNotExistsException.class, () -> underTest.deleteProduct(productId));

        // then
        verify(productRepository).findById(productId);
        verifyNoMoreInteractions(productRepository);
    }
}