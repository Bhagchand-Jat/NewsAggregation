package com.newsaggregator.client.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.newsaggregator.client.dto.CategoryStatusDTO;
import com.newsaggregator.client.dto.KeywordDTO;
import com.newsaggregator.client.dto.NotificationDTO;
import com.newsaggregator.client.service.BaseService;
import com.newsaggregator.client.service.NotificationService;
import com.newsaggregator.client.util.Constant;
import com.newsaggregator.client.util.UiText;

import java.util.List;

public class NotificationServiceImpl extends BaseService implements NotificationService {


    @Override
    public List<NotificationDTO> viewNotifications(Long userId) {
        String url = Constant.API_USER_NOTIFICATIONS
                .replace(Constant.USER_ID, String.valueOf(userId));
        return safePatchList(url, new TypeReference<List<NotificationDTO>>() {
        });

    }


    @Override
    public void addKeywordsToCategory(Long userId, Long categoryId, List<String> keywords) {
        String url = Constant.API_USERS_NOTIFICATIONS_PREFERENCES_KEYWORDS
                .replace(Constant.USER_ID, String.valueOf(userId));
        for (String keyword : keywords) {
            KeywordDTO keywordDTO = new KeywordDTO(keyword, userId);
            KeywordDTO keywordSaved = safePost(url, keywordDTO, new TypeReference<KeywordDTO>() {
            });
        }

    }

    @Override
    public void deleteUserKeyword(Long userId, Long keywordId) {
        String url = Constant.API_USERS_NOTIFICATIONS_PREFERENCES_KEYWORDS_ID
                .replace(Constant.USER_ID, String.valueOf(userId))
                .replace(Constant.KEYWORD_ID, String.valueOf(keywordId));
        safeDelete(url, "Keyword Deleted Successfully");
    }


    @Override
    public List<CategoryStatusDTO> getCategoriesStatus(Long userId) {
        String url = Constant.API_USERS_NOTIFICATIONS_PREFERENCES_CATEGORIES_LIST
                .replace(Constant.USER_ID, String.valueOf(userId));
        return safeGetList(url, new TypeReference<>() {
        });

    }

    @Override
    public void updateCategoryStatus(Long userId, Long categoryId, boolean isEnabled) {
        String url = Constant.API_USERS_NOTIFICATIONS_PREFERENCES_CATEGORIES
                .replace(Constant.USER_ID, String.valueOf(userId))
                .replace(Constant.CATEGORY_ID, String.valueOf(categoryId));
        if (!isEnabled) {
            safeDelete(url, UiText.CATEGORY_DISABLE_SUCCESS);
        } else {
            safePost(url, null, new TypeReference<Void>() {
            });
        }
    }
}
