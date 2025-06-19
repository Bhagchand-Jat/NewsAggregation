package com.news_aggregation_system.service.common;

import org.springframework.stereotype.Service;

@Service
public class ValidationServiceImpl implements ValidationService {

    @Override
    public boolean isValidEmail(String email) {
        throw new UnsupportedOperationException("Unimplemented method 'isValidEmail'");
    }

    @Override
    public boolean isValidUrl(String url) {
        return url != null && url.startsWith("http");
    }

}
