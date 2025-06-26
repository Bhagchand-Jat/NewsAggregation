package com.news_aggregation_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class ArticleReportDTO {

    private Long id;

    @NotNull
    private Long articleId;

    @NotNull
    private Long userId;

    @NotBlank(message = "reason is required")
    private String reason;

    @NotNull
    private Date reportedAt = new Date();

    public ArticleReportDTO() {

    }

    public ArticleReportDTO(Long articleId, Long userId, String reason) {
        this.articleId = articleId;
        this.userId = userId;
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getReportedAt() {
        return reportedAt;
    }

    public void setReportedAt(Date reportedAt) {
        this.reportedAt = reportedAt;
    }
}
