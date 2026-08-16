package com.midterm.midterm.exception;

public class CannotDeleteAdminException extends RuntimeException {

    public CannotDeleteAdminException(String message) {
        super(message);
    }
}