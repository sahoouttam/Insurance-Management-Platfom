package com.project.imp.exception;

public class ResourceNotException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceNotException(String message) {
        super(message);
    }
}
