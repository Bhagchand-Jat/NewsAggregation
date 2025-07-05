package com.news_aggregation_system.controller;

import com.news_aggregation_system.dto.LoginRequest;
import com.news_aggregation_system.dto.UserDTO;
import com.news_aggregation_system.response.ApiResponse;
import com.news_aggregation_system.service.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.news_aggregation_system.service.common.Constant.LOGIN_SUCCESS;
import static com.news_aggregation_system.service.common.Constant.SIGNUP_SUCCESS;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserDTO>> login(@Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(ApiResponse.ok(LOGIN_SUCCESS, authService.login(request)));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserDTO>> signUp(@Valid @RequestBody UserDTO userDTO) {

        return ResponseEntity.ok(ApiResponse.ok(SIGNUP_SUCCESS, authService.register(userDTO)));
    }
}
