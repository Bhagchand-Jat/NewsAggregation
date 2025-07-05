package com.news_aggregation_system.controller;

import com.news_aggregation_system.dto.ArticleReactionDTO;
import com.news_aggregation_system.response.ApiResponse;
import com.news_aggregation_system.service.user.ArticleReactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.news_aggregation_system.service.common.Constant.*;

@RestController
@RequestMapping("/api/reactions")
public class ArticleReactionController {

    private final ArticleReactionService articleReactionService;


    public ArticleReactionController(ArticleReactionService articleReactionService) {
        this.articleReactionService = articleReactionService;
    }

    @PostMapping("/like")
    public ResponseEntity<ApiResponse<ArticleReactionDTO>> like(@Valid @RequestBody ArticleReactionDTO dto) {
        dto.setReactionType(LIKE);
        return ResponseEntity.ok(ApiResponse.ok(ARTICLE_LIKED_SUCCESS,
                articleReactionService.reactToArticle(dto)));
    }

    @PostMapping("/dislike")
    public ResponseEntity<ApiResponse<ArticleReactionDTO>> dislike(@Valid @RequestBody ArticleReactionDTO dto) {
        dto.setReactionType(DISLIKE);
        return ResponseEntity.ok(ApiResponse.ok(ARTICLE_DIS_LIKED_SUCCESS,
                articleReactionService.reactToArticle(dto)));
    }
}
