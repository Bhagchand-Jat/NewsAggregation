package com.news_aggregation_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.news_aggregation_system.dto.CategoryDTO;
import com.news_aggregation_system.dto.KeywordDTO;
import com.news_aggregation_system.dto.KeywordListRequest;
import com.news_aggregation_system.dto.NewsSourceDTO;
import com.news_aggregation_system.model.Role;
import com.news_aggregation_system.model.User;
import com.news_aggregation_system.security.CustomUserDetails;
import com.news_aggregation_system.security.JwtUtil;
import com.news_aggregation_system.service.admin.CategoryService;
import com.news_aggregation_system.service.admin.KeywordService;
import com.news_aggregation_system.service.admin.NewsSourceService;
import com.news_aggregation_system.service.admin.SystemConfigService;
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

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired private MockMvc mvc;
    @Autowired private JwtUtil jwtUtil;
    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    private NewsSourceService newsSourceService;
    @MockitoBean private CategoryService categoryService;
    @MockitoBean private NewsAggregationService newsAggregationService;

    @MockitoBean
    private CustomUserDetailsService userDetailsService;

    private String jwtToken;

    @MockitoBean
    private SystemConfigService configService;
    @Autowired
    private KeywordService keywordService;

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
    @DisplayName("POST /api/admin/categories returns 200 OK")
    void createCategory_post_returnsOk() throws Exception {
       
        lenient().when(newsSourceService.toString()).thenReturn("mock");
        
        lenient().when(categoryService.toString()).thenReturn("mock");

        lenient().when(newsAggregationService.toString()).thenReturn("mock");

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("mock");

        mvc.perform(post("/api/admin/categories")
                .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/admin/categories returns 200 OK")
    void getCategories_get_returnsOk() throws Exception {

        lenient().when(newsSourceService.toString()).thenReturn("mock");

        lenient().when(categoryService.toString()).thenReturn("mock");

        lenient().when(newsAggregationService.toString()).thenReturn("mock");

        mvc.perform(get("/api/admin/categories")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PATCH /api/admin/categories/1 returns 200 OK")
    void updateCategory_patch_returnsOk() throws Exception {

        lenient().when(newsSourceService.toString()).thenReturn("mock");

        lenient().when(categoryService.toString()).thenReturn("mock");

        lenient().when(newsAggregationService.toString()).thenReturn("mock");

        mvc.perform(patch("/api/admin/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .queryParam("isEnabled", String.valueOf(false)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /api/admin/categories/1 returns 200 OK")
    void deleteCategory_delete_returnsOk() throws Exception {

        lenient().when(newsSourceService.toString()).thenReturn("mock");

        lenient().when(categoryService.toString()).thenReturn("mock");

        lenient().when(newsAggregationService.toString()).thenReturn("mock");

        mvc.perform(delete("/api/admin/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/admin/news-sources returns 200 OK")
    void getAllNewsSources_get_returnsOk() throws Exception {

        lenient().when(newsSourceService.toString()).thenReturn("mock");

        lenient().when(categoryService.toString()).thenReturn("mock");

        lenient().when(newsAggregationService.toString()).thenReturn("mock");

        mvc.perform(get("/api/admin/news-sources")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /api/admin/news-sources returns 200 OK")
    void createNewsSource_post_returnsOk() throws Exception {

        lenient().when(newsSourceService.toString()).thenReturn("mock");

        lenient().when(categoryService.toString()).thenReturn("mock");

        lenient().when(newsAggregationService.toString()).thenReturn("mock");
        NewsSourceDTO newsSourceDTO=new NewsSourceDTO();
        newsSourceDTO.setSourceName("mock");
        newsSourceDTO.setEnabled(true);
        newsSourceDTO.setSourceUrl("mock");
        newsSourceDTO.setSourceApiKey("mock");
        mvc.perform(post("/api/admin/news-sources")
                .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .content(objectMapper.writeValueAsString(newsSourceDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PATCH /api/admin/news-sources/1/apiKey returns 200 OK")
    void updateNewsSource_patch_returnsOk() throws Exception {

        lenient().when(newsSourceService.toString()).thenReturn("mock");

        lenient().when(categoryService.toString()).thenReturn("mock");

        lenient().when(newsAggregationService.toString()).thenReturn("mock");

        mvc.perform(patch("/api/admin/news-sources/1/apikey")
                .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .queryParam("newApiKey","ghjkl")
                .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT /api/admin/news-sources returns 200 OK")
    void newsSources_put_returnsOk() throws Exception {

        lenient().when(newsSourceService.toString()).thenReturn("mock");

        lenient().when(categoryService.toString()).thenReturn("mock");

        lenient().when(newsAggregationService.toString()).thenReturn("mock");

        mvc.perform(put("/api/admin/news-sources")
                .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/admin/news-sources/1 returns 200 OK")
    void getNewsSourceById_get_returnsOk() throws Exception {

        lenient().when(newsSourceService.toString()).thenReturn("mock");

        lenient().when(categoryService.toString()).thenReturn("mock");

        lenient().when(newsAggregationService.toString()).thenReturn("mock");

        mvc.perform(get("/api/admin/news-sources/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/admin/categories/1/keywords returns 200 OK")
    void getKeywordsForCategory_returnsOk() throws Exception {
        lenient().when(categoryService.toString()).thenReturn("mock");

        mvc.perform(get("/api/admin/categories/1/keywords")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /api/admin/categories/1/keywords returns 200 OK")
    void addKeywordsToCategory_returnsOk() throws Exception {
        KeywordListRequest request = new KeywordListRequest();
        request.setKeywords(List.of("AI", "Politics"));

        mvc.perform(post("/api/admin/categories/1/keywords")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /api/admin/keywords returns 200 OK")
    void createKeyword_returnsOk() throws Exception {

        lenient().when(categoryService.toString()).thenReturn("mock");
        lenient().when(keywordService.toString()).thenReturn("mock");

        KeywordDTO keyword = new KeywordDTO();
        keyword.setName("tech");
        keyword.setCategoryId(1L);
        mvc.perform(post("/api/admin/keywords")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(keyword)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PATCH /api/admin/categories/1/keywords/AI returns 200 OK")
    void updateKeywordStatus_returnsOk() throws Exception {

        lenient().when(categoryService.toString()).thenReturn("mock");

        lenient().when(keywordService.toString()).thenReturn("mock");

        mvc.perform(patch("/api/admin/categories/1/keywords/AI")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("isEnabled", "true"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /api/admin/categories/1/keywords/AI returns 200 OK")
    void deleteKeyword_returnsOk() throws Exception {
        mvc.perform(delete("/api/admin/categories/1/keywords/AI")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PATCH /api/admin/articles/1/status returns 200 OK")
    void updateArticleStatus_returnsOk() throws Exception {
        mvc.perform(patch("/api/admin/articles/1/status")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("isEnabled", "true"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/admin/reported-articles returns 200 OK")
    void getReportedArticles_returnsOk() throws Exception {
        mvc.perform(get("/api/admin/reported-articles")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/admin/1/article-reports returns 200 OK")
    void getArticleReports_returnsOk() throws Exception {
        mvc.perform(get("/api/admin/1/article-reports")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/admin returns 200 OK")
    void getAllUsers_returnsOk() throws Exception {
        mvc.perform(get("/api/admin")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }


}