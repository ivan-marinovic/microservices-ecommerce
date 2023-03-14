package com.ivan.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivan.product.dto.ProductRequest;
import com.ivan.product.dto.ProductResponse;
import com.ivan.product.model.Product;
import com.ivan.product.service.ProductPresentationService;
import com.ivan.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @Mock
    private ProductPresentationService productPresentationService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void canGetAllProducts() throws Exception {
        // given
        List<Product> productList = Arrays.asList(
                Product.builder().id(1).name("Test Product 1").description("Test Description 1").price(10.0F).build(),
                Product.builder().id(2).name("Test Product 2").description("Test Description 2").price(20.0F).build()
        );

        when(productService.getAllProducts()).thenReturn(productList);
        when(productPresentationService.convertToDtoList(productList)).thenReturn(Arrays.asList(
                ProductResponse.builder().id(1).name("Test Product 1").description("Test Description 1").price(10.0F).build(),
                ProductResponse.builder().id(2).name("Test Product 2").description("Test Description 2").price(20.0F).build()
        ));

        // when
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // then
        verify(productService).getAllProducts();
        verify(productPresentationService).convertToDtoList(productList);

        String expectedResponse = new ObjectMapper().writeValueAsString(Arrays.asList(
                ProductResponse.builder().id(1).name("Test Product 1").description("Test Description 1").price(10.0F).build(),
                ProductResponse.builder().id(2).name("Test Product 2").description("Test Description 2").price(20.0F).build()
        ));

        assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString());
    }

}