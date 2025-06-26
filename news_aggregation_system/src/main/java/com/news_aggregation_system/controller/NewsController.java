package com.news_aggregation_system.controller;

import com.news_aggregation_system.dto.ArticleDTO;
import com.news_aggregation_system.response.ApiResponse;
import com.news_aggregation_system.service.news.NewsAggregationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ArticleDTO>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(newsService.getById(id)));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ArticleDTO>>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(ApiResponse.ok(newsService.searchArticlesByKeyword(keyword)));
    }

    @GetMapping("/category")
    public ResponseEntity<ApiResponse<List<ArticleDTO>>> getByCategory(@RequestParam String name) {
        List<ArticleDTO> articles = newsService.getArticlesByCategory(name);
        return ResponseEntity.ok(ApiResponse.ok("Articles in category sorted by reactions", articles));
    }

    @GetMapping("/date")
    public ResponseEntity<ApiResponse<List<ArticleDTO>>> getByDate(
            @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        List<ArticleDTO> articles = newsService.getArticlesByDate(date);
        return ResponseEntity.ok(ApiResponse.ok("Articles on date sorted by reactions", articles));
    }

    @GetMapping("/range")
    public ResponseEntity<ApiResponse<List<ArticleDTO>>> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date to) {
        List<ArticleDTO> articles = newsService.getArticlesByDateRange(from, to);
        return ResponseEntity.ok(ApiResponse.ok("Articles in range sorted by reactions", articles));
    }


    @GetMapping("/popular")
    public ResponseEntity<ApiResponse<List<ArticleDTO>>> getPopularArticles() {
        List<ArticleDTO> articles = newsService.getArticlesSortedByLikesAndDislikes();
        return ResponseEntity.ok(ApiResponse.ok("Articles sorted by popularity", articles));
    }
}
