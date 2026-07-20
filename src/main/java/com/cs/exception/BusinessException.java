package com.cs.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);  // 传给父类
    }
}
