package com.newsaggregator.client.dto;


import com.newsaggregator.client.model.ReactionType;

public class ArticleReactionDTO {
    private Long reactionId;

    private String reactionType;

    private Long articleId;

    private Long userId;

    public ArticleReactionDTO() {
    }

    public ArticleReactionDTO(Long articleId, ReactionType reactionType) {
        this.articleId = articleId;
        this.userId = 0L;
        this.reactionType = reactionType.name();
    }

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
