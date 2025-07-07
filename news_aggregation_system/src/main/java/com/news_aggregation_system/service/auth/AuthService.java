package com.news_aggregation_system.service.auth;

import com.news_aggregation_system.dto.LoginRequest;
import com.news_aggregation_system.dto.UserDTO;
import com.news_aggregation_system.response.JwtAuthResponse;

public interface AuthService {

    JwtAuthResponse login(LoginRequest request);

    JwtAuthResponse register(UserDTO user);

    void logout(Long userId);

    JwtAuthResponse refresh(String refreshToken);


}
