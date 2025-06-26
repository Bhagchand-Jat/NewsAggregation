package com.news_aggregation_system.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "news_sources")
public class NewsSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sourceId;

    @Column(nullable = false)
    private String sourceName;

    @Column(nullable = false)
    private String sourceUrl;

    @Column(nullable = false)
    private String sourceApiKey;

    @Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime lastAccessed;

    @Column(nullable = false)
    private boolean enabled=true;


    public NewsSource() {
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

    public LocalDateTime getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(LocalDateTime lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}

