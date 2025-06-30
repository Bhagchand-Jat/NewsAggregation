package com.newsaggregator.client.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.newsaggregator.client.dto.LoginRequest;
import com.newsaggregator.client.dto.UserDTO;
import com.newsaggregator.client.service.AuthService;
import com.newsaggregator.client.service.BaseService;
import com.newsaggregator.client.session.UserSession;
import com.newsaggregator.client.util.Constant;

import java.util.Optional;

public class AuthServiceImpl extends BaseService implements AuthService {

    @Override
    public Optional<UserSession> login(String email, String password) {
        LoginRequest loginRequest = new LoginRequest(email, password);
        String url = Constant.LOGIN_URL;

        UserDTO loggedInUser = safePost(url, loginRequest, new TypeReference<>() {
        });

        if (loggedInUser == null) {
            return Optional.empty();
        } else {
            return Optional.of(new UserSession(loggedInUser));
        }


    }

    @Override
    public Optional<UserSession> signup(String name, String email, String password) {
        UserDTO newUser = new UserDTO(email, name, password);
        String url = Constant.SIGNUP_URL;
        UserDTO signUpUser = safePost(url, newUser, new TypeReference<>() {
        });

        if (signUpUser == null) {
            return Optional.empty();
        } else {
            return Optional.of(new UserSession(signUpUser));
        }
    }

    @Override
    public void logout(String userId) {
        System.out.println("User logged out: " + userId);
    }
}


