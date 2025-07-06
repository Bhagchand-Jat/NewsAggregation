package com.newsaggregator.client.dto;

public class KeywordDTO {
    private Long keywordId;

    private String name;

    private Long userId;

    private boolean enabled = true;
    private Long categoryId;

    public KeywordDTO() {
    }

    public KeywordDTO(String name, Long userId) {
        this.name = name;
        this.userId = userId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
