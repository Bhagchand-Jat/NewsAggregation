package com.news_aggregation_system.service.common;

public interface ValidationService {
    boolean isValidEmail(String email);

    boolean isValidUrl(String url);
}
