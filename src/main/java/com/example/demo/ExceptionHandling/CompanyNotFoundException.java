package com.example.demo.ExceptionHandling;

public class CompanyNotFoundException extends  RuntimeException{
    public CompanyNotFoundException(String message) {
        super(message);
    }
}
