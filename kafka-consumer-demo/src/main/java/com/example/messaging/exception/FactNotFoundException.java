package com.example.messaging.exception;

public class FactNotFoundException extends RuntimeException {

    public FactNotFoundException(String message) {
        super("Could not find fact with id: " + message);
    }

}
