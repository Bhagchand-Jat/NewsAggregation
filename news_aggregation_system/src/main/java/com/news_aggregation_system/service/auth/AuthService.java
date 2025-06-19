package com.news_aggregation_system.service.auth;

import com.news_aggregation_system.dto.LoginRequest;
import com.news_aggregation_system.dto.LoginResponse;
import com.news_aggregation_system.dto.UserDTO;

public interface AuthService {

    UserDTO login(LoginRequest request);

    UserDTO register(UserDTO user);

    void logout(Long userId);


}
