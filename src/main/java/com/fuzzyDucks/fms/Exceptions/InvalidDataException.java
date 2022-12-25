package com.fuzzyDucks.fms.Exceptions;

public class InvalidDataException extends IllegalArgumentException {
    public InvalidDataException(String message) {
        super(message);
    }
}
