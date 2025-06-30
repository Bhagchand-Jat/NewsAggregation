package com.news_aggregation_system.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String resourceName, String identifier) {
        super(resourceName + " not found with " + identifier);
    }

    public NotFoundException(String message) {
        super(message);
    }
}
