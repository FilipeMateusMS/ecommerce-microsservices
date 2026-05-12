package com.filipe.ecommerce.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message ) {
        super( message );
    }
}
