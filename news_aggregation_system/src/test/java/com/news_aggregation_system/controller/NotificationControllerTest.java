package com.news_aggregation_system.controller;

import com.news_aggregation_system.model.Role;
import com.news_aggregation_system.model.User;
import com.news_aggregation_system.security.CustomUserDetails;
import com.news_aggregation_system.security.JwtUtil;
import com.news_aggregation_system.service.auth.CustomUserDetailsService;
import com.news_aggregation_system.service.notification.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class NotificationControllerTest {

    @Autowired private MockMvc mvc;
    @Autowired private JwtUtil jwtUtil;

    @MockitoBean
    private CustomUserDetailsService userDetailsService;

    private String jwtToken;

    @BeforeEach
    void setup() {

        User user = new User();
        user.setUserId(123L);
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setName("test");
        Role role=new Role();
        role.setRole("USER");
        user.setRole(role);
        UserDetails testUser = new CustomUserDetails(user);

        when(userDetailsService.loadUserByUsername("test@example.com"))
                .thenReturn(testUser);

        jwtToken = jwtUtil.generateAccessToken(testUser);
    }

    @MockitoBean
    private NotificationService notificationService;

    @Test
    @DisplayName("PATCH /api/users/me/notifications returns 200 OK")
    void filterNotifications_patch_returnsOk() throws Exception {
        lenient().when(notificationService.toString()).thenReturn("mock");

        mvc.perform(patch("/api/users/me/notifications")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("isViewed", String.valueOf(false))
                        .content("{}"))
                .andExpect(status().isOk());
    }

}