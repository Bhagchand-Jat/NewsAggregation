package com.newsaggregator.client.service;

import com.newsaggregator.client.dto.*;

import java.util.List;

public interface AdminService {
    List<NewsSourceDTO> allNewsSources();

    void updateNewsSourceApiKey(Long serverId, String apiKey);

    void addCategory(String categoryName);

    void updateCategoryStatus(Long categoryId, boolean isEnabled);

    List<CategoryDTO> allCategories();

    void updateNewsArticleStatus(Long articleId, boolean isEnabled);

    Long getArticleReportThreshold();

    void updateArticleReportThreshold(int updatedThreshold);

    List<ArticleDTO> getAllReportedArticles();

    List<ArticleReportDTO> getAllReportsOfArticle(Long articleId);

    void addKeywordsToCategory(Long categoryId, List<String> keywords);

    void updateKeywordStatus(Long categoryId, String keyword, boolean isEnabled);

    void deleteKeyword(Long categoryId, String keyword);

    List<KeywordDTO> getAllKeywordsForCategory(Long categoryId);
}
