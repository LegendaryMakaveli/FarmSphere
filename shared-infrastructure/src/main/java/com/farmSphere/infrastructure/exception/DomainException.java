package com.farmSphere.infrastructure.exception;

public class DomainException extends RuntimeException {

    private final int statusCode;

    public DomainException(String message) {
        super(message);
        this.statusCode = 400;
    }

    public DomainException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
