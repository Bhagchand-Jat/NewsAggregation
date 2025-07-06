package com.news_aggregation_system.dto;

import jakarta.validation.constraints.NotBlank;

public class KeywordDTO {
    private Long keywordId;

    @NotBlank(message = "Keyword name is required")
    private String name;

    private boolean enabled;

    private Long categoryId;

    public KeywordDTO() {
    }

    public Long getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(Long keywordId) {
        this.keywordId = keywordId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
