package com.example.ecommerceonebox.exception;

import lombok.Data;

@Data
public class CartServiceCustomException extends RuntimeException{
    private String errorCode;

    public CartServiceCustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
