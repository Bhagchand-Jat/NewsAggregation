package com.news_aggregation_system.service.user;

import com.news_aggregation_system.dto.CategoryStatusDTO;

import java.util.List;
import java.util.Set;

public interface CategoryPreferenceService {

    void enableCategoryForUser(Long userId, Long categoryId);

    void disableCategoryForUser(Long userId, Long categoryId);


    List<CategoryStatusDTO> getEnabledCategoriesStatus(Long userId);

    void addKeywordsToCategory(Long userId,
                               Long categoryId,
                               List<String> words);

    Set<String> getEnabledKeywords(Long userId);

    List<String> getEnabledKeywordsForCategory(Long userId, Long categoryId);

    void deleteKeywordFromCategory(Long userId, Long categoryId, String keywordName);
}

