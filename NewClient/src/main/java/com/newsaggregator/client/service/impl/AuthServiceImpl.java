package com.newsaggregator.client.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.newsaggregator.client.dto.LoginRequest;
import com.newsaggregator.client.dto.TokenRefreshRequest;
import com.newsaggregator.client.dto.UserDTO;
import com.newsaggregator.client.response.JwtAuthResponse;
import com.newsaggregator.client.service.AuthService;
import com.newsaggregator.client.service.BaseService;
import com.newsaggregator.client.service.UserService;
import com.newsaggregator.client.session.TokenHolder;
import com.newsaggregator.client.util.Constant;

import java.util.Optional;

public class AuthServiceImpl extends BaseService implements AuthService {

    private final UserService userService;

    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void login(String email, String password) {
        LoginRequest loginRequest = new LoginRequest(email, password);
        String url = Constant.LOGIN_URL;

        JwtAuthResponse jwtAuthResponse = safePost(url, loginRequest, new TypeReference<>() {
        });

        if (jwtAuthResponse != null) {
            TokenHolder.setTokens(jwtAuthResponse.accessToken(),
                    jwtAuthResponse.refreshToken());
            Optional<UserDTO> user = userService.getUser();
            if (user.isPresent()) {

                UserDTO userDTO = user.get();
                TokenHolder.setName(userDTO.getName());
                TokenHolder.setRole(userDTO.getRole().getRole());

            } else {
                TokenHolder.clear();
            }



        }


    }

    @Override
    public void signup(String name, String email, String password) {
        UserDTO newUser = new UserDTO(email, name, password);
        String url = Constant.SIGNUP_URL;
        JwtAuthResponse jwtAuthResponse = safePost(url, newUser, new TypeReference<>() {
        });

        if (jwtAuthResponse != null) {
            TokenHolder.setTokens(jwtAuthResponse.accessToken(),
                    jwtAuthResponse.refreshToken());
            Optional<UserDTO> user = userService.getUser();
            if (user.isPresent()) {

                UserDTO userDTO = user.get();
                TokenHolder.setName(userDTO.getName());
                TokenHolder.setRole(userDTO.getRole().getRole());
            } else {
                TokenHolder.clear();
            }
        }
    }

    @Override
    public Optional<JwtAuthResponse> refreshToken(String refreshToken) {
        String url = Constant.REFRESH_URL;
        JwtAuthResponse jwtAuthResponse = safePost(url, new TokenRefreshRequest(refreshToken), new TypeReference<>() {
        });
        if (jwtAuthResponse != null) {
            return Optional.of(jwtAuthResponse);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void logout() {
        TokenHolder.clear();
        System.out.println("User logged out ");
    }
}


