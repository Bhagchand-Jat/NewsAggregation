package com.newsaggregator.client.dto;

import java.util.HashSet;
import java.util.Set;

public class NotificationConfigDTO {
    private Long id;
    private Long userId;
    private boolean keywordsEnabled;
    private Set<UserCategoryPreferenceDTO> userCategoryPreferences = new HashSet<>();

    public NotificationConfigDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isKeywordsEnabled() {
        return keywordsEnabled;
    }

    public void setKeywordsEnabled(boolean keywordsEnabled) {
        this.keywordsEnabled = keywordsEnabled;
    }

    public Set<UserCategoryPreferenceDTO> getUserCategoryPreferences() {
        return userCategoryPreferences;
    }

    public void setUserCategoryPreferences(Set<UserCategoryPreferenceDTO> userCategoryPreferences) {
        this.userCategoryPreferences = userCategoryPreferences;
    }

}
