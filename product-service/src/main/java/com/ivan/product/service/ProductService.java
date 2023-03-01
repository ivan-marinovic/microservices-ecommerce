package com.ivan.product.service;

import com.ivan.product.exception.ProductDoesNotExistsException;
import com.ivan.product.model.Product;
import com.ivan.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProduct(Integer id) throws ProductDoesNotExistsException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()) {
            throw new ProductDoesNotExistsException("product with id " + id + " does not exists");
        }
        productRepository.deleteById(id);
    }
}
