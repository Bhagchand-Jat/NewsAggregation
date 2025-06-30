package com.newsaggregator.client.service;

import com.newsaggregator.client.dto.CategoryDTO;
import com.newsaggregator.client.dto.NewsSourceDTO;

import java.util.List;

public interface AdminService {
    List<NewsSourceDTO> allNewsSources();

    void updateNewsSourceApiKey(Long serverId, String apiKey);

    void addCategory(String categoryName);

    void updateCategoryStatus(Long categoryId, boolean isEnabled);

    List<CategoryDTO> allCategories();

    void updateNewsArticleStatus(Long articleId, boolean isEnabled);
}
