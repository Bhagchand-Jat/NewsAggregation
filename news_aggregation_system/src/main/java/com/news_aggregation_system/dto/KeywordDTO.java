package com.news_aggregation_system.dto;

import jakarta.validation.constraints.NotBlank;

public class KeywordDTO {
    private Long keywordId;

    @NotBlank(message = "Keyword name is required")
    private String name;

    private Long userId;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


}
