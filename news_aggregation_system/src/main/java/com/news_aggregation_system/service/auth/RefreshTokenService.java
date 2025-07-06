package com.news_aggregation_system.service.auth;

public interface RefreshTokenService {
     void saveOrUpdate(String email, String token, long expiryMs);
     boolean isValidToken(String email, String token);
}
