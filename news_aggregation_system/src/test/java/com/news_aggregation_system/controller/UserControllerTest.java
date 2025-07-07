package com.news_aggregation_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.news_aggregation_system.dto.ArticleReportDTO;
import com.news_aggregation_system.dto.SavedArticleDTO;
import com.news_aggregation_system.dto.UserDTO;
import com.news_aggregation_system.model.Role;
import com.news_aggregation_system.model.User;
import com.news_aggregation_system.security.CustomUserDetails;
import com.news_aggregation_system.security.JwtUtil;
import com.news_aggregation_system.service.admin.CategoryService;
import com.news_aggregation_system.service.auth.CustomUserDetailsService;
import com.news_aggregation_system.service.news.NewsAggregationService;
import com.news_aggregation_system.service.user.ArticleReadHistoryService;
import com.news_aggregation_system.service.user.SavedArticleService;
import com.news_aggregation_system.service.user.UserService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired private MockMvc mvc;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private ObjectMapper mapper;

    @MockitoBean
    private UserService userService;
    @MockitoBean private SavedArticleService savedArticleService;
    @MockitoBean private CategoryService categoryService;
    @MockitoBean private NewsAggregationService newsAggregationService;
    @MockitoBean private ArticleReadHistoryService articleReadHistoryService;

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

        lenient().when(savedArticleService.getSavedArticlesByUser(anyLong())).thenReturn(List.of());
        lenient().when(categoryService.getEnabledCategories()).thenReturn(List.of());
        lenient().when(newsAggregationService.getAllArticlesReportsByUserId(anyLong())).thenReturn(List.of());
        lenient().when(articleReadHistoryService.getArticleReadHistory(anyLong())).thenReturn(List.of());
    }

    @Test @DisplayName("PUT /me → 200")
    void updateUser() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setEmail("x@y.com");
        dto.setPassword("password1234P&");
        dto.setName("x");
        when(userService.update(anyLong(), any())).thenReturn(dto);
        mvc.perform(put("/api/users/me")
                        .header(HttpHeaders.AUTHORIZATION, jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test @DisplayName("DELETE /me → 200")
    void deleteUser() throws Exception {
        mvc.perform(delete("/api/users/me")
                        .header(HttpHeaders.AUTHORIZATION, jwtToken))
                .andExpect(status().isOk());
    }


    @Test @DisplayName("GET /me/saved-articles → 200")
    void getSavedArticles() throws Exception {
        mvc.perform(get("/api/users/me/saved-articles")
                        .header(HttpHeaders.AUTHORIZATION, jwtToken))
                .andExpect(status().isOk());
    }

    @Test @DisplayName("POST /save-article → 200")
    void saveArticle() throws Exception {
        SavedArticleDTO dto = new SavedArticleDTO();
        dto.setArticleId(10L); dto.setUserId(555L);
        mvc.perform(post("/api/users/save-article")
                        .header(HttpHeaders.AUTHORIZATION, jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test @DisplayName("DELETE /me/saved-article/{id} → 200")
    void deleteSavedArticle() throws Exception {
        mvc.perform(delete("/api/users/me/saved-article/10")
                        .header(HttpHeaders.AUTHORIZATION, jwtToken))
                .andExpect(status().isOk());
    }


    @Test @DisplayName("GET /categories/enabled → 200")
    void getEnabledCategories() throws Exception {
        mvc.perform(get("/api/users/categories/enabled")
                        .header(HttpHeaders.AUTHORIZATION, jwtToken))
                .andExpect(status().isOk());
    }


    @Test @DisplayName("POST /report-article → 200")
    void reportArticle() throws Exception {
        ArticleReportDTO dto = new ArticleReportDTO();
        dto.setArticleId(20L); dto.setReason("spam");
        dto.setUserId(0L);
        mvc.perform(post("/api/users/report-article")
                        .header(HttpHeaders.AUTHORIZATION, jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test @DisplayName("GET /me/articles-reports → 200")
    void getArticleReports() throws Exception {
        mvc.perform(get("/api/users/me/articles-reports")
                        .header(HttpHeaders.AUTHORIZATION, jwtToken))
                .andExpect(status().isOk());
    }


    @Test @DisplayName("POST /me/article/{id}/markAsRead → 200")
    void markAsRead() throws Exception {
        mvc.perform(post("/api/users/me/article/33/markAsRead")
                        .header(HttpHeaders.AUTHORIZATION, jwtToken))
                .andExpect(status().isOk());
    }

    @Test @DisplayName("GET /me/articles-read-history → 200")
    void readHistory() throws Exception {
        mvc.perform(get("/api/users/me/articles-read-history")
                        .header(HttpHeaders.AUTHORIZATION, jwtToken))
                .andExpect(status().isOk());
    }
}