package com.codewithmosh.store.exceptions;

public class CartItemNotFoundException extends RuntimeException {
    public  CartItemNotFoundException(String message) {
        super(message);
    }
    public CartItemNotFoundException(){
    }
}
