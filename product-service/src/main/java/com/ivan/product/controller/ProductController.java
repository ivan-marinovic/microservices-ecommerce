package com.ivan.product.controller;

import com.ivan.product.dto.ProductRequest;
import com.ivan.product.dto.ProductResponse;
import com.ivan.product.model.Product;
import com.ivan.product.response.ApiResponse;
import com.ivan.product.service.ProductPresentationService;
import com.ivan.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    private final ProductService productService;
    private final ProductPresentationService productPresentationService;

    public ProductController(ProductService productService, ProductPresentationService productPresentationService) {
        this.productService = productService;
        this.productPresentationService = productPresentationService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(productPresentationService.convertToDtoList(products), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        productService.createProduct(productPresentationService.convertToModel(productRequest));
        return new ResponseEntity<>(new ApiResponse(1, "product successfully created"), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("id") Integer id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(new ApiResponse(1, "product successfully deleted"), HttpStatus.OK);
    }

}
