package com.news_aggregation_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.news_aggregation_system.dto.ArticleReactionDTO;
import com.news_aggregation_system.model.ReactionType;
import com.news_aggregation_system.model.Role;
import com.news_aggregation_system.model.User;
import com.news_aggregation_system.security.CustomUserDetails;
import com.news_aggregation_system.security.JwtUtil;
import com.news_aggregation_system.service.auth.CustomUserDetailsService;
import com.news_aggregation_system.service.user.ArticleReactionService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ArticleReactionControllerTest {


    @MockitoBean
    private ArticleReactionService articleReactionService;

    @Autowired private MockMvc mvc;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private ObjectMapper mapper;

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

    @Test
    @DisplayName("POST /api/reactions/like returns 200 OK")
    void like_post_returnsOk() throws Exception {

        lenient().when(articleReactionService.toString()).thenReturn("mock");

        ArticleReactionDTO dto=new ArticleReactionDTO();
        dto.setArticleId(1L);
        dto.setUserId(123L);
        dto.setReactionType(ReactionType.LIKE.toString());

        mvc.perform(post("/api/reactions/like")
                .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /api/reactions/dislike returns 200 OK")
    void dislike_post_returnsOk() throws Exception {
        lenient().when(articleReactionService.toString()).thenReturn("mock");
        ArticleReactionDTO dto=new ArticleReactionDTO();
        dto.setArticleId(1L);
        dto.setUserId(123L);
        dto.setReactionType(ReactionType.DISLIKE.toString());


        mvc.perform(post("/api/reactions/dislike")
                .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

}