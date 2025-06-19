package com.news_aggregation_system.mapper;

import com.news_aggregation_system.dto.CategoryDTO;
import com.news_aggregation_system.model.Category;
import com.news_aggregation_system.model.User;

public class CategoryMapper {

    public static CategoryDTO toDto(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryId(category.getCategoryId());
        dto.setName(category.getName());
        if (category.getUser() != null) {
            dto.setUserId(category.getUser().getUserId());
        }
        return dto;
    }

    public static Category toEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());

        if (dto.getUserId() != null) {
            User user = new User();
            user.setUserId(dto.getUserId());
            category.setUser(user);
        }

        return category;
    }
}
