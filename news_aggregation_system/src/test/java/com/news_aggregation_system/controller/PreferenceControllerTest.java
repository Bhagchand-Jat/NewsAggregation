package com.news_aggregation_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.news_aggregation_system.dto.KeywordListRequest;
import com.news_aggregation_system.model.Role;
import com.news_aggregation_system.model.User;
import com.news_aggregation_system.security.CustomUserDetails;
import com.news_aggregation_system.security.JwtUtil;
import com.news_aggregation_system.service.admin.KeywordService;
import com.news_aggregation_system.service.auth.CustomUserDetailsService;
import com.news_aggregation_system.service.user.CategoryPreferenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PreferenceControllerTest {

    @Autowired private MockMvc mvc;

    @MockitoBean
    private CategoryPreferenceService categoryPreferenceService;
    @MockitoBean
    private KeywordService keywordService;

    @Autowired private JwtUtil jwtUtil;
    @Autowired
    private ObjectMapper objectMapper;

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

        jwtToken ="Bearer " + jwtUtil.generateAccessToken(testUser);
        lenient().when(categoryPreferenceService.getEnabledCategoriesStatus(anyLong()))
                .thenReturn(List.of());
        lenient().when(categoryPreferenceService.getEnabledKeywords(anyLong()))
                .thenReturn(Set.of());
    }

    @Test @DisplayName("POST /categories/{id} → 200")
    void enableCategory() throws Exception {
        mvc.perform(post("/api/users/me/notifications/preferences/categories/1")
                        .header(HttpHeaders.AUTHORIZATION, jwtToken))
                .andExpect(status().isOk());
    }

    @Test @DisplayName("DELETE /categories/{id} → 200")
    void disableCategory() throws Exception {
        mvc.perform(delete("/api/users/me/notifications/preferences/categories/1")
                        .header(HttpHeaders.AUTHORIZATION, jwtToken))
                .andExpect(status().isOk());
    }


    @Test @DisplayName("POST /categories/{id}/keywords → 200")
    void addKeywords() throws Exception {
        KeywordListRequest keywordListRequest = new KeywordListRequest();
        keywordListRequest.setKeywords(List.of("foo","bar"));

        mvc.perform(post("/api/users/me/notifications/preferences/categories/1/keywords")
                        .header(HttpHeaders.AUTHORIZATION, jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(keywordListRequest)))
                .andExpect(status().isOk());
    }

    @Test @DisplayName("DELETE /categories/{id}/keywords?keyword=foo → 200")
    void deleteKeyword() throws Exception {
        mvc.perform(delete("/api/users/me/notifications/preferences/categories/1/keywords")
                        .param("keyword","foo")
                        .header(HttpHeaders.AUTHORIZATION, jwtToken))
                .andExpect(status().isOk());
    }


    @Test @DisplayName("GET /categories → 200")
    void getEnabledCategories() throws Exception {
        mvc.perform(get("/api/users/me/notifications/preferences/categories")
                        .header(HttpHeaders.AUTHORIZATION, jwtToken))
                .andExpect(status().isOk());
    }

    @Test @DisplayName("GET /categories/{id}/keywords → 200")
    void getKeywordsForCategory() throws Exception {
        mvc.perform(get("/api/users/me/notifications/preferences/categories/1/keywords")
                        .header(HttpHeaders.AUTHORIZATION, jwtToken))
                .andExpect(status().isOk());
    }

    @Test @DisplayName("GET /keywords → 200")
    void getAllEnabledKeywords() throws Exception {
        mvc.perform(get("/api/users/me/notifications/preferences/keywords")
                        .header(HttpHeaders.AUTHORIZATION, jwtToken))
                .andExpect(status().isOk());
    }
}