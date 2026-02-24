package com.codewithmosh.store.exceptions;

public class EmptyCartException extends RuntimeException {
    public EmptyCartException(String cartIsEmpty) {
        super(cartIsEmpty);
    }
}
