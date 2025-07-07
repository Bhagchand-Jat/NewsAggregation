package com.news_aggregation_system.controller;

import com.news_aggregation_system.dto.UserDTO;
import com.news_aggregation_system.response.ApiResponse;
import com.news_aggregation_system.security.CustomUserDetails;
import com.news_aggregation_system.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-details/me")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class UserDetailsController {
    public UserDetailsController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@AuthenticationPrincipal CustomUserDetails user) {
        UserDTO userDTO = userService.getById(user.getUserId());
        return ResponseEntity.ok(ApiResponse.ok(userDTO));
    }
}
