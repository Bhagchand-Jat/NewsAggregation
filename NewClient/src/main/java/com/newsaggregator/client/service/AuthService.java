package com.newsaggregator.client.service;

import com.newsaggregator.client.session.UserSession;

import java.util.Optional;

public interface AuthService {
    Optional<UserSession> login(String email, String password);

    Optional<UserSession> signup(String username, String email, String password);

    void logout(String userId);
}
