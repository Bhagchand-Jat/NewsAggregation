package com.news_aggregation_system.service.auth;

import com.news_aggregation_system.dto.LoginRequest;
import com.news_aggregation_system.dto.UserDTO;
import com.news_aggregation_system.repository.RoleRepository;
import com.news_aggregation_system.repository.UserRepository;
import com.news_aggregation_system.response.JwtAuthResponse;
import com.news_aggregation_system.security.JwtUtil;
import com.news_aggregation_system.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

public class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl service;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private UserService userService;


    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("login - success")
    void login_success() {
        LoginRequest request = new LoginRequest();
        request.setPassword("8Password@");
        request.setEmail("test@gmail.com");
        JwtAuthResponse result = service.login(request);
        assertThat(result).isNotNull();

    }

    @Test
    @DisplayName("register - success")
    void register_success() {
        UserDTO user = new UserDTO();
        user.setUserId(1L);
        user.setName("test");
        user.setPassword("8Password@");
        user.setEmail("test@gmail.com");
        when(userService.create(user)).thenReturn(user);
        JwtAuthResponse result = service.register(user);
        assertThat(result).isNotNull();
        
    }

    @Test
    @DisplayName("logout - success")
    void logout_success() {
        Long userId = 0L;
        assertThatThrownBy(()->service.logout(userId));

    }

}