package com.codewithmosh.store.exceptions;

import lombok.NoArgsConstructor;


public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(String message) {
        super(message);
    }
    public CartNotFoundException(){

    };
}
