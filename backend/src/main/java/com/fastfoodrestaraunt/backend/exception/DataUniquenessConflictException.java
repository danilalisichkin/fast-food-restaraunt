package com.fastfoodrestaraunt.backend.exception;

public class DataUniquenessConflictException extends RuntimeException {
    public DataUniquenessConflictException(String message) {
        super(message);
    }
}
