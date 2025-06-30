package com.news_aggregation_system.exception;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String resourceName, String identifier) {
        super(resourceName + " already exists with " + identifier);
    }

    public AlreadyExistsException(String message) {
        super(message);
    }
}
