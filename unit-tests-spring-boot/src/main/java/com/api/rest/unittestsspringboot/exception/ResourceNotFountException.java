package com.api.rest.unittestsspringboot.exception;

public class ResourceNotFountException extends RuntimeException{
    
    public ResourceNotFountException(String  mensaje){
        super(mensaje);
    }
}
