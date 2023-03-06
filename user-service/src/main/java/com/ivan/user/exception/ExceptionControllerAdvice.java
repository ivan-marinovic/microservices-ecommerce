package com.ivan.user.exception;

import com.ivan.user.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = UserDoesNotExistsException.class)
    public final ResponseEntity<ApiResponse> userDoesNotExistsException(UserDoesNotExistsException userDoesNotExistsException) {
        return new ResponseEntity<>(new ApiResponse(0, userDoesNotExistsException.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public final ResponseEntity<ApiResponse> userDoesNotExistsException(UserAlreadyExistsException userAlreadyExistsException) {
        return new ResponseEntity<>(new ApiResponse(0, userAlreadyExistsException.getMessage()), HttpStatus.FOUND);
    }


}
