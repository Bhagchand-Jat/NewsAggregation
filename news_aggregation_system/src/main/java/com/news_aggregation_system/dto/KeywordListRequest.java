package com.news_aggregation_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class KeywordListRequest {
    @NotEmpty(message = "Keywords list cannot be empty")
    List<@NotBlank(message = "Keyword cannot be blank") String> keywords;

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
