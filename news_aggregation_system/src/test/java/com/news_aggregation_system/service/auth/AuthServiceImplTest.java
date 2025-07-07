package com.news_aggregation_system.service.auth;

import com.news_aggregation_system.dto.LoginRequest;
import com.news_aggregation_system.dto.UserDTO;
import com.news_aggregation_system.response.JwtAuthResponse;
import com.news_aggregation_system.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl service;


    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("login - success")
    void login_happyPath() {
        LoginRequest request = new LoginRequest();
        request.setPassword("8Password@");
        request.setEmail("test@gmail.com");
        JwtAuthResponse result = service.login(request);
        assertThat(result).isNotNull();

    }

    @Test
    @DisplayName("register - success")
    void success() {
        UserDTO user = new UserDTO();
        user.setName("test");
        user.setPassword("8Password@");
        user.setEmail("test@gmail.com");
        JwtAuthResponse result = service.register(user);
        assertThat(result).isNotNull();
        
    }

    @Test
    @DisplayName("logout - success")
    void logout_happyPath() {
        Long userId = 0L;
        service.logout(userId);

    }

}