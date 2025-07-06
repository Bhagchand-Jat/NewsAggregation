package com.newsaggregator.client.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.newsaggregator.client.dto.*;
import com.newsaggregator.client.service.AdminService;
import com.newsaggregator.client.service.BaseService;
import com.newsaggregator.client.util.Constant;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static com.newsaggregator.client.util.Constant.*;

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
                .fromUriString(Constant.API_ADMIN_ARTICLES_STATUS.replace(ARTICLE_ID, articleId.toString()))
                .queryParam(Constant.IS_ENABLED, isEnabled)
                .build()
                .toUriString();
        safePatch(url, new TypeReference<Void>() {
        });
    }

    @Override
    public Long getArticleReportThreshold() {

        return safeGet(Constant.API_ADMIN_CONFIG_THRESHOLD, new TypeReference<>() {
        });
    }

    @Override
    public void updateArticleReportThreshold(int updatedThreshold) {
        String url = UriComponentsBuilder
                .fromUriString(Constant.API_ADMIN_CONFIG_THRESHOLD)
                .queryParam(Constant.NEW_THRESHOLD, updatedThreshold)
                .build()
                .toUriString();
        safePatch(url, new TypeReference<Void>() {
        });
    }

    @Override
    public List<ArticleDTO> getAllReportedArticles() {
        return safeGetList(Constant.API_ADMIN_REPORTED_ARTICLES, new TypeReference<>() {
        });
    }

    @Override
    public List<ArticleReportDTO> getAllReportsOfArticle(Long articleId) {
        String url = Constant.API_ADMIN_ARTICLE_REPORTS
                .replace(ARTICLE_ID, articleId.toString());
        return safeGetList(url, new TypeReference<>() {
        });
    }

    @Override
    public void addKeywordsToCategory(Long categoryId, List<String> keywords) {
        String url = Constant.API_ADMIN_CATEGORIES_KEYWORDS
                .replace(CATEGORY_ID, categoryId.toString());
        safePost(url, new KeywordListRequest(keywords), new TypeReference<>() {
        });
    }

    @Override
    public void updateKeywordStatus(Long categoryId, String keyword, boolean isEnabled) {
        String url = UriComponentsBuilder.fromUriString(Constant.API_ADMIN_CATEGORY_KEYWORDS_NAME
                        .replace(CATEGORY_ID, categoryId.toString())
                        .replace(KEYWORD_NAME, keyword))
                .queryParam(IS_ENABLED, isEnabled)
                .build()
                .toUriString();
        safePatch(url, new TypeReference<Void>() {
        });

    }

    @Override
    public void deleteKeyword(Long categoryId, String keyword) {
        String url = API_ADMIN_CATEGORY_KEYWORDS_NAME
                .replace(CATEGORY_ID, categoryId.toString())
                .replace(KEYWORD_NAME, keyword);
        safeDelete(url, "Keyword Deleted Successfully.");
    }

    @Override
    public List<KeywordDTO> getAllKeywordsForCategory(Long categoryId) {
        String url = Constant.API_ADMIN_CATEGORIES_KEYWORDS
                .replace(CATEGORY_ID, categoryId.toString());
        return safeGetList(url, new TypeReference<>() {
        });
    }
}
