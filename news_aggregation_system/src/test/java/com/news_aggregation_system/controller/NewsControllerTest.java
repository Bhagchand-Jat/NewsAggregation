package com.news_aggregation_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.news_aggregation_system.dto.ArticleFilterRequestDTO;
import com.news_aggregation_system.model.Role;
import com.news_aggregation_system.model.User;
import com.news_aggregation_system.security.CustomUserDetails;
import com.news_aggregation_system.security.JwtUtil;
import com.news_aggregation_system.service.auth.CustomUserDetailsService;
import com.news_aggregation_system.service.news.NewsAggregationService;
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

import java.time.Instant;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class NewsControllerTest {

    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper objectMapper;

    @MockitoBean
    private NewsAggregationService newsService;

    @Autowired private JwtUtil jwtUtil;

    @MockitoBean
    private CustomUserDetailsService userDetailsService;

    private String jwtUserToken;
    private String jwtAdminToken;


    @BeforeEach
    void setup() {

        User admin = new User();
        admin.setUserId(123L);
        admin.setEmail("admin@example.com");
        admin.setPassword("password");
        admin.setName("test");
        Role adminRole=new Role();
        adminRole.setRole("ADMIN");
        admin.setRole(adminRole);
        UserDetails adminUser = new CustomUserDetails(admin);

        User user = new User();
        user.setUserId(123L);
        user.setEmail("user@example.com");
        user.setPassword("password");
        user.setName("test");
        Role role=new Role();
        role.setRole("USER");
        user.setRole(role);
        UserDetails testUser = new CustomUserDetails(user);

        when(userDetailsService.loadUserByUsername(admin.getEmail()))
                .thenReturn(adminUser);

        when(userDetailsService.loadUserByUsername(user.getEmail()))
                .thenReturn(testUser);

        jwtUserToken = jwtUtil.generateAccessToken(testUser);
        jwtAdminToken = jwtUtil.generateAccessToken(adminUser);
    }

    @Test
    @DisplayName("GET /api/news returns 200 OK")
    void listAll_get_returnsOk() throws Exception {

        lenient().when(newsService.toString()).thenReturn("mock");

        mvc.perform(get("/api/news")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtAdminToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /api/news/me/filter returns 200 OK")
    void filterArticles_today_post_returnsOk() throws Exception {
        lenient().when(newsService.toString()).thenReturn("mock");
        ArticleFilterRequestDTO filter=new ArticleFilterRequestDTO();

        filter.setDate(new Date());

        mvc.perform(post("/api/news/me/filter")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUserToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filter)))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("POST /api/news/me/filter returns 200 OK")
    void filterArticles_dateRange_And_Category_post_returnsOk() throws Exception {
        lenient().when(newsService.toString()).thenReturn("mock");
        ArticleFilterRequestDTO filter=new ArticleFilterRequestDTO();

        filter.setFrom(Date.from(Instant.parse("2025-07-05T00:00:00Z")));
        filter.setTo(Date.from(Instant.parse("2025-07-07T00:00:00Z")));

        mvc.perform(post("/api/news/me/filter")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUserToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filter)))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("POST /api/news/me/filter returns 200 OK")
    void filterArticles_keyword_post_returnsOk() throws Exception {
        lenient().when(newsService.toString()).thenReturn("mock");
        ArticleFilterRequestDTO filter=new ArticleFilterRequestDTO();

        filter.setKeyword("test");

        mvc.perform(post("/api/news/me/filter")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUserToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filter)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/news/1 returns 200 OK")
    void getByArticleId_get_returnsOk() throws Exception {

        lenient().when(newsService.toString()).thenReturn("mock");

        mvc.perform(get("/api/news/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUserToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}