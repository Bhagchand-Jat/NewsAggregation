package com.adminclient.dto;

public class NewsSourceDTO {
    private Long sourceId;

    private String sourceUrl;

    private String sourceApiKey;

    private boolean enabled=true;


    public NewsSourceDTO() {
    }

    public NewsSourceDTO(long id, String apiKey, boolean active) {
        //TODO Auto-generated constructor stub
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
