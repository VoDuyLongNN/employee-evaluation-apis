package com.example.employeeevaluation.exceptionhandler.customerException;

public class ApiValidateException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public ApiValidateException(String msg) {
        super(msg);
    }
}
