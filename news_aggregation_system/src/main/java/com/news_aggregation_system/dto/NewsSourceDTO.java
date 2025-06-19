package com.news_aggregation_system.dto;

import jakarta.validation.constraints.NotBlank;

public class NewsSourceDTO {
    private Long sourceId;

    @NotBlank(message = "Source URL is required")
    private String sourceUrl;

    @NotBlank(message = "Source API Key is required")
    private String sourceApiKey;

    private boolean enabled=true;


    public NewsSourceDTO() {
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getSourceApiKey() {
        return sourceApiKey;
    }

    public void setSourceApiKey(String sourceApiKey) {
        this.sourceApiKey = sourceApiKey;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
