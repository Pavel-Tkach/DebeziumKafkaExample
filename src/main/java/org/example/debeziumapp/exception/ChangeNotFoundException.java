package org.example.debeziumapp.exception;

public class ChangeNotFoundException extends RuntimeException {

    public ChangeNotFoundException(String message) {
        super(message);
    }
}
