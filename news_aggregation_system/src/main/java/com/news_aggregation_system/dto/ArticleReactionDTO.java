package com.news_aggregation_system.dto;

import jakarta.validation.constraints.NotNull;

public class ArticleReactionDTO {
    private Long reactionId;

    @NotNull(message = "Reaction type is required")
    private String reactionType;

    @NotNull(message = "Article ID is required")
    private Long articleId;

    @NotNull(message = "User ID is required")
    private Long userId;

    public Long getReactionId() {
        return reactionId;
    }

    public void setReactionId(Long reactionId) {
        this.reactionId = reactionId;
    }

    public String getReactionType() {
        return reactionType;
    }

    public void setReactionType(String reactionType) {
        this.reactionType = reactionType;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
