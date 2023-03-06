package com.ivan.user.exception;

public class UserDoesNotExistsException extends RuntimeException{
    public UserDoesNotExistsException(String msg) {
        super(msg);
    }
}
