package com.newsaggregator.client.dto;

import java.time.LocalDateTime;

public class SavedArticleDTO {
    private Long userId;
    private Long articleId;
    private LocalDateTime savedAt;

    public SavedArticleDTO() {
    }

    public SavedArticleDTO(Long articleId) {
        this.userId = 0L;
        this.articleId = articleId;
    }

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
