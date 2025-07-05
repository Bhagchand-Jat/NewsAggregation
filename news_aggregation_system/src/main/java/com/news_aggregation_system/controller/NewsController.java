package com.news_aggregation_system.controller;

import com.news_aggregation_system.dto.ArticleDTO;
import com.news_aggregation_system.dto.ArticleFilterRequestDTO;
import com.news_aggregation_system.response.ApiResponse;
import com.news_aggregation_system.service.news.NewsAggregationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    private final NewsAggregationService newsService;


    public NewsController(NewsAggregationService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ArticleDTO>>> listAll() {
        return ResponseEntity.ok(ApiResponse.ok(newsService.getAll()));
    }

    @PostMapping("/{userId}/filter")
    public ResponseEntity<ApiResponse<List<ArticleDTO>>> filterArticles(@PathVariable Long userId, @RequestBody ArticleFilterRequestDTO articleFilterRequestDTO) {
        return ResponseEntity.ok(ApiResponse.ok(newsService.filterArticles(userId, articleFilterRequestDTO)));
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<ApiResponse<ArticleDTO>> getByArticleId(@PathVariable Long articleId) {
        return ResponseEntity.ok(ApiResponse.ok(newsService.getById(articleId)));
    }

}
