package com.news_aggregation_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.news_aggregation_system.dto.LoginRequest;
import com.news_aggregation_system.dto.TokenRefreshRequest;
import com.news_aggregation_system.dto.UserDTO;
import com.news_aggregation_system.service.auth.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {


    @MockitoBean
    private AuthService authService;

    @Autowired private MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/auth/login returns 200 OK")
    void login_post_returnsOk() throws Exception {

        lenient().when(authService.toString()).thenReturn("mock");
         LoginRequest loginRequest = new LoginRequest();
         loginRequest.setEmail("test");
         loginRequest.setPassword("password");
        mvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /api/auth/signup returns 200 OK")
    void signUp_post_returnsOk() throws Exception {

        lenient().when(authService.toString()).thenReturn("mock");
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test");
        userDTO.setPassword("password");
        userDTO.setName("test");
        mvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /api/auth/refresh returns 200 OK")
    void refresh_post_returnsOk() throws Exception {
        lenient().when(authService.toString()).thenReturn("mock");

        mvc.perform(post("/api/auth/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new TokenRefreshRequest("fghjkl"))))
                .andExpect(status().isOk());
    }

}