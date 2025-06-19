package com.news_aggregation_system.service.admin;

import com.news_aggregation_system.dto.CategoryDTO;
import com.news_aggregation_system.model.Category;
import com.news_aggregation_system.service.BaseService;


import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

public interface CategoryService extends BaseService<CategoryDTO, Long> {

    Category getOrCreateCategory(String name);

    Set<Category> getOrCreateCategories(Set<String> names);

    CategoryDTO getByCategoryName(String name);

    List<CategoryDTO> getEnabledCategories();
}
