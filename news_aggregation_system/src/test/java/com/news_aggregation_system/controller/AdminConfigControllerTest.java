package com.news_aggregation_system.controller;

import com.news_aggregation_system.model.Role;
import com.news_aggregation_system.model.User;
import com.news_aggregation_system.security.CustomUserDetails;
import com.news_aggregation_system.security.JwtUtil;
import com.news_aggregation_system.service.admin.SystemConfigService;
import com.news_aggregation_system.service.auth.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminConfigControllerTest {

    @Autowired private MockMvc mvc;
    @Autowired private JwtUtil jwtUtil;

    @MockitoBean
    private CustomUserDetailsService userDetailsService;

    private String jwtToken;

    @MockitoBean
    private SystemConfigService configService;

    @BeforeEach
    void setup() {

        User user = new User();
        user.setUserId(123L);
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setName("test");
        Role role=new Role();
        role.setRole("ADMIN");
        user.setRole(role);
        UserDetails testUser = new CustomUserDetails(user);

        when(userDetailsService.loadUserByUsername("test@example.com"))
                .thenReturn(testUser);

        jwtToken = jwtUtil.generateAccessToken(testUser);
    }

    @Test
    @DisplayName("GET /api/admin/config/threshold returns 200 OK")
    void getThreshold_get_returnsOk() throws Exception {
        lenient().when(configService.toString()).thenReturn("mock");

        mvc.perform(get("/api/admin/config/threshold")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PATCH /api/admin/config/threshold returns 200 OK")
    void updateThreshold_get_returnsOk() throws Exception {
        lenient().when(configService.toString()).thenReturn("mock");
        int thresholdValue=3;
        mvc.perform(patch("/api/admin/config/threshold"+"?newThreshold="+thresholdValue)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}