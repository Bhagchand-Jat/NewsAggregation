package com.news_aggregation_system.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class SavedArticleDTO {
    @NotNull(message = "UserId is required")
    private Long userId;
    @NotNull(message = "ArticleId is required")
    private Long articleId;

    private LocalDateTime savedAt = LocalDateTime.now();

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public LocalDateTime getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(LocalDateTime savedAt) {
        this.savedAt = savedAt;
    }
}
