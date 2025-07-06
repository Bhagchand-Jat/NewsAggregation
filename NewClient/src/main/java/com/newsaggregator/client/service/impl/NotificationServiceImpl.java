package com.newsaggregator.client.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.newsaggregator.client.dto.CategoryStatusDTO;
import com.newsaggregator.client.dto.KeywordListRequest;
import com.newsaggregator.client.dto.NotificationDTO;
import com.newsaggregator.client.service.BaseService;
import com.newsaggregator.client.service.NotificationService;
import com.newsaggregator.client.util.Constant;
import com.newsaggregator.client.util.UiText;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public class NotificationServiceImpl extends BaseService implements NotificationService {


    @Override
    public List<NotificationDTO> viewNotifications(Long userId) {
        String url = Constant.API_USER_NOTIFICATIONS
                .replace(Constant.USER_ID, String.valueOf(userId));
        return safePatchList(url, new TypeReference<>() {
        });

    }


    @Override
    public void addKeywordsToCategory(Long userId, Long categoryId, List<String> keywords) {
        String url = Constant.API_USERS_NOTIFICATIONS_PREFERENCES_KEYWORDS
                .replace(Constant.USER_ID, String.valueOf(userId))
                .replace(Constant.CATEGORY_ID, String.valueOf(categoryId));

        safePost(url, new KeywordListRequest(keywords), new TypeReference<Void>() {
        });

    }

    @Override
    public void deleteUserKeyword(Long userId, Long categoryId, String keyword) {
        String url = UriComponentsBuilder.fromUriString(Constant.API_USERS_NOTIFICATIONS_PREFERENCES_KEYWORDS
                        .replace(Constant.USER_ID, String.valueOf(userId))
                        .replace(Constant.CATEGORY_ID, categoryId.toString()))
                .queryParam("keyword", keyword)
                .build().toUriString();
        safeDelete(url, "Keyword Deleted Successfully");
    }


    @Override
    public List<CategoryStatusDTO> getCategoriesStatus(Long userId) {
        String url = Constant.API_USERS_NOTIFICATIONS_PREFERENCES_CATEGORIES_LIST
                .replace(Constant.USER_ID, userId.toString());
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

    @Override
    public List<String> viewKeywordsForCategory(Long userId, Long categoryId) {
        String url = Constant.API_USERS_NOTIFICATIONS_PREFERENCES_KEYWORDS
                .replace(Constant.USER_ID, String.valueOf(userId))
                .replace(Constant.CATEGORY_ID, categoryId.toString());
        return safeGetList(url, new TypeReference<>() {
        });
    }
}
