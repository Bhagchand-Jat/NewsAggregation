package com.news_aggregation_system.service.user;

import com.news_aggregation_system.dto.CategoryStatusDTO;
import com.news_aggregation_system.dto.UserCategoryPreferenceDTO;
import com.news_aggregation_system.model.Category;

import java.util.List;

public interface CategoryPreferenceService {

    UserCategoryPreferenceDTO createPreference(Long userId, Long categoryId, boolean enabled);

    void deletePreference(Long userId, Long categoryId);

    void enableCategoryForUser(Long userId, Long categoryId);

    void disableCategoryForUser(Long userId, Long categoryId);

    List<Category> getEnabledCategories(Long userId);

    List<CategoryStatusDTO> getEnabledCategoriesStatus(Long userId);
}

