package com.newsaggregator.client.service;

import com.newsaggregator.client.dto.CategoryStatusDTO;
import com.newsaggregator.client.dto.NotificationDTO;

import java.util.List;

public interface NotificationService {
    List<NotificationDTO> viewNotifications();


    void addKeywordsToCategory(Long categoryId, List<String> keyword);

    void deleteUserKeyword(Long categoryId, String keyword);

    List<CategoryStatusDTO> getCategoriesStatus();

    void updateCategoryStatus(Long categoryId, boolean isEnabled);

    List<String> viewKeywordsForCategory(Long categoryId);
}
