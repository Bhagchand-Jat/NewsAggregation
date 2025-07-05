package com.news_aggregation_system.service.admin;

import com.news_aggregation_system.dto.CategoryDTO;
import com.news_aggregation_system.model.Category;
import com.news_aggregation_system.service.BaseService;

import java.util.List;
import java.util.Set;

public interface CategoryService extends BaseService<CategoryDTO, Long> {


    Set<Category> getOrCreateCategories(Set<String> names);

    CategoryDTO getByCategoryName(String name);

    List<CategoryDTO> getEnabledCategories();

    void updateCategoryStatus(Long categoryId, boolean isEnabled);
}
