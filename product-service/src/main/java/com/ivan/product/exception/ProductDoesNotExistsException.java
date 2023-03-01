package com.ivan.product.exception;

public class ProductDoesNotExistsException extends RuntimeException {
    public ProductDoesNotExistsException(String msg) {
        super(msg);
    }
}
