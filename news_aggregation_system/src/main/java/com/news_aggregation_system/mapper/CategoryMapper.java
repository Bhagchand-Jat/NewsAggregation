package com.news_aggregation_system.mapper;

import com.news_aggregation_system.dto.CategoryDTO;
import com.news_aggregation_system.model.Category;

public class CategoryMapper {

    public static CategoryDTO toDto(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryId(category.getCategoryId());
        dto.setName(category.getName());
        dto.setEnabled(category.isEnabled());
        if (category.getKeywords() != null) {
            dto.setKeywords(category.getKeywords());
        }


        return dto;
    }

    public static Category toEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setEnabled(dto.isEnabled());
        category.setKeywords(dto.getKeywords());

        return category;
    }
}
