package com.filipe.ecommerce.exception;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode( callSuper = true )
@Data
public class CustomerNotFoundException extends RuntimeException {

    private final String message;

// Generate this code    
//    public CustomerNotFoundException( String message ){
//        super( message);
//    }
}
