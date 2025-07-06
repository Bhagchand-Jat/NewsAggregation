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
    public List<NotificationDTO> viewNotifications() {
        String url = Constant.API_USERS_NOTIFICATIONS;
        return safePatchList(url, new TypeReference<>() {
        });

    }


    @Override
    public void addKeywordsToCategory(Long categoryId, List<String> keywords) {
        String url = Constant.API_USERS_NOTIFICATIONS_PREFERENCES_KEYWORDS

                .replace(Constant.CATEGORY_ID, String.valueOf(categoryId));

        safePost(url, new KeywordListRequest(keywords), new TypeReference<Void>() {
        });

    }

    @Override
    public void deleteUserKeyword(Long categoryId, String keyword) {
        String url = UriComponentsBuilder.fromUriString(Constant.API_USERS_NOTIFICATIONS_PREFERENCES_KEYWORDS

                        .replace(Constant.CATEGORY_ID, categoryId.toString()))
                .queryParam("keyword", keyword)
                .build().toUriString();
        safeDelete(url, "Keyword Deleted Successfully");
    }


    @Override
    public List<CategoryStatusDTO> getCategoriesStatus() {
        String url = Constant.API_USERS_NOTIFICATIONS_PREFERENCES_CATEGORIES_LIST;
        return safeGetList(url, new TypeReference<>() {
        });

    }

    @Override
    public void updateCategoryStatus(Long categoryId, boolean isEnabled) {
        String url = Constant.API_USERS_NOTIFICATIONS_PREFERENCES_CATEGORIES

                .replace(Constant.CATEGORY_ID, String.valueOf(categoryId));
        if (!isEnabled) {
            safeDelete(url, UiText.CATEGORY_DISABLE_SUCCESS);
        } else {
            safePost(url, null, new TypeReference<Void>() {
            });
        }
    }

    @Override
    public List<String> viewKeywordsForCategory(Long categoryId) {
        String url = Constant.API_USERS_NOTIFICATIONS_PREFERENCES_KEYWORDS

                .replace(Constant.CATEGORY_ID, categoryId.toString());
        return safeGetList(url, new TypeReference<>() {
        });
    }
}
