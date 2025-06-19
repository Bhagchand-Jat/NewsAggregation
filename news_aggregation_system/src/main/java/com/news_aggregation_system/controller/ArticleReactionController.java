package com.news_aggregation_system.controller;

import com.news_aggregation_system.dto.ArticleReactionDTO;
import com.news_aggregation_system.response.ApiResponse;
import com.news_aggregation_system.service.user.ArticleReactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reactions")
public class ArticleReactionController {

    private final ArticleReactionService articleReactionService;


    public ArticleReactionController(ArticleReactionService articleReactionService) {
        this.articleReactionService = articleReactionService;
    }

    @PostMapping("/like")
    public ResponseEntity<ApiResponse<ArticleReactionDTO>> like(@RequestBody ArticleReactionDTO dto) {
        dto.setReactionType("LIKE");
        return ResponseEntity.ok(ApiResponse.ok("Article liked",
                articleReactionService.reactToArticle(dto)));
    }

    @PostMapping("/dislike")
    public ResponseEntity<ApiResponse<ArticleReactionDTO>> dislike(@RequestBody ArticleReactionDTO dto) {
        dto.setReactionType("DISLIKE");
        return ResponseEntity.ok(ApiResponse.ok("Article disliked",
                articleReactionService.reactToArticle(dto)));
    }
}
