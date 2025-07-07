package com.news_aggregation_system.controller;

import com.news_aggregation_system.dto.ArticleDTO;
import com.news_aggregation_system.dto.ArticleFilterRequestDTO;
import com.news_aggregation_system.response.ApiResponse;
import com.news_aggregation_system.security.CustomUserDetails;
import com.news_aggregation_system.service.news.NewsAggregationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    private final NewsAggregationService newsService;


    public NewsController(NewsAggregationService newsService) {
        this.newsService = newsService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ArticleDTO>>> listAll() {
        return ResponseEntity.ok(ApiResponse.ok(newsService.getAll()));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/me/filter")
    public ResponseEntity<ApiResponse<List<ArticleDTO>>> filterArticles(@AuthenticationPrincipal CustomUserDetails user, @RequestBody ArticleFilterRequestDTO articleFilterRequestDTO) {
        return ResponseEntity.ok(ApiResponse.ok(newsService.filterArticles(user.getUserId(), articleFilterRequestDTO)));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{articleId}")
    public ResponseEntity<ApiResponse<ArticleDTO>> getByArticleId(@PathVariable Long articleId) {
        return ResponseEntity.ok(ApiResponse.ok(newsService.getById(articleId)));
    }

}
