package com.newsaggregator.client.dto;

import java.time.LocalDateTime;

public class SourceDTO {
    private Long sourceId;

    private String sourceName;
    private String sourceUrl;

    private String sourceApiKey;

    private boolean enabled = true;

    private LocalDateTime lastAccessed;

    public SourceDTO() {
    }

    public SourceDTO(Long sourceId, String sourceApiKey, boolean enabled) {
        this.sourceId = sourceId;
        this.sourceApiKey = sourceApiKey;
        this.enabled = enabled;
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

    public LocalDateTime getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(LocalDateTime lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }


}