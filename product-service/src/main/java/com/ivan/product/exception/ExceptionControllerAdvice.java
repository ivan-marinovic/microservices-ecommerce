package com.ivan.product.exception;

import com.ivan.product.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = ProductDoesNotExistsException.class)
    public final ResponseEntity<ApiResponse> userDoesNotExistsException(ProductDoesNotExistsException productDoesNotExistsException) {
        return new ResponseEntity<>(new ApiResponse(0, productDoesNotExistsException.getMessage()), HttpStatus.NOT_FOUND);
    }


}
