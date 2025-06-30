package com.newsaggregator.client.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.newsaggregator.client.dto.CategoryDTO;
import com.newsaggregator.client.dto.NewsSourceDTO;
import com.newsaggregator.client.service.AdminService;
import com.newsaggregator.client.service.BaseService;
import com.newsaggregator.client.util.Constant;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public class AdminServiceImpl extends BaseService implements AdminService {

    @Override
    public void updateNewsSourceApiKey(Long serverId, String apiKey) {
        String url = UriComponentsBuilder
                .fromUriString(Constant.API_ADMIN_NEWS_SOURCES_APIKEY.replace(Constant.NEWS_SOURCE_ID, serverId.toString()))
                .queryParam(Constant.NEW_API_KEY, apiKey)
                .build()
                .toUriString();
        safePatch(url, new TypeReference<Void>() {
        });
    }

    @Override
    public void addCategory(String categoryName) {
        CategoryDTO category = new CategoryDTO(categoryName);
        safePost(Constant.API_ADMIN_CATEGORIES, category, new TypeReference<>() {
        });
    }

    @Override
    public void updateCategoryStatus(Long categoryId, boolean isEnabled) {
        String url = UriComponentsBuilder
                .fromUriString(Constant.API_ADMIN_CATEGORIES_ID.replace(Constant.CATEGORY_ID, categoryId.toString()))
                .queryParam(Constant.IS_ENABLED, isEnabled)
                .build()
                .toUriString();
        safePatch(url, new TypeReference<Void>() {
        });
    }

    @Override
    public List<NewsSourceDTO> allNewsSources() {
        return safeGetList(Constant.API_ADMIN_NEWS_SOURCES, new TypeReference<>() {
        });
    }

    @Override
    public List<CategoryDTO> allCategories() {
        return safeGetList(Constant.API_ADMIN_CATEGORIES, new TypeReference<>() {
        });
    }

    @Override
    public void updateNewsArticleStatus(Long articleId, boolean isEnabled) {
        String url = UriComponentsBuilder
                .fromUriString(Constant.API_ADMIN_ARTICLES_STATUS.replace(Constant.ARTICLE_ID, articleId.toString()))
                .queryParam(Constant.IS_ENABLED, isEnabled)
                .build()
                .toUriString();
        safePatch(url, new TypeReference<Void>() {
        });
    }
}
