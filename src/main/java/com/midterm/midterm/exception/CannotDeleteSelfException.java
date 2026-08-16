package com.midterm.midterm.exception;

public class CannotDeleteSelfException extends RuntimeException {
    public CannotDeleteSelfException(String message) {
        super(message);
    }
}