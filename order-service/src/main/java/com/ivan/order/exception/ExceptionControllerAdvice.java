package com.ivan.order.exception;

import com.ivan.order.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = OutOfStockException.class)
    public final ResponseEntity<ApiResponse> userDoesNotExistsException(OutOfStockException outOfStockException) {
        return new ResponseEntity<>(new ApiResponse(0, outOfStockException.getMessage()), HttpStatus.NOT_FOUND);
    }


}
