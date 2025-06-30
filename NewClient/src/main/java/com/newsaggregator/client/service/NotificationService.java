package com.newsaggregator.client.service;

import com.newsaggregator.client.dto.CategoryStatusDTO;
import com.newsaggregator.client.dto.NotificationDTO;

import java.util.List;

public interface NotificationService {
    List<NotificationDTO> viewNotifications(Long userId);


    void addKeywordsToCategory(Long userId, Long categoryId, List<String> keyword);

    void deleteUserKeyword(Long userId, Long keywordId);

    List<CategoryStatusDTO> getCategoriesStatus(Long userId);

    void updateCategoryStatus(Long userId, Long categoryId, boolean isEnabled);
}
