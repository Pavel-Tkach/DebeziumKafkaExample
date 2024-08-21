package org.example.debeziumapp.exception;

public class TableNameNotSpecifiedException extends RuntimeException{

    public TableNameNotSpecifiedException(String message) {
        super(message);
    }
}
