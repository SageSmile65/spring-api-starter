package com.codewithmosh.store.exceptions;

public class WrongfulOrderMapping extends RuntimeException {
    public WrongfulOrderMapping(String message) {
        super(message);
    }
}
