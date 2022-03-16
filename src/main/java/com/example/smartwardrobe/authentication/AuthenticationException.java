package com.example.smartwardrobe.authentication;

public class AuthenticationException extends Exception{

    public AuthenticationException(String errorMessage, Throwable error){
        super(errorMessage, error);
    }

}
