package com.newsaggregator.client.service;

import com.newsaggregator.client.response.JwtAuthResponse;

import java.util.Optional;

public interface AuthService {
    void login(String email, String password);

    void signup(String username, String email, String password);

    Optional<JwtAuthResponse> refreshToken(String refreshToken);

    void logout();
}
