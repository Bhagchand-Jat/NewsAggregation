package com.news_aggregation_system.mapper;

import com.news_aggregation_system.dto.UserCategoryPreferenceDTO;
import com.news_aggregation_system.model.Category;
import com.news_aggregation_system.model.User;
import com.news_aggregation_system.model.UserCategoryPreference;

public class UserCategoryPreferenceMapper {

    public static UserCategoryPreferenceDTO toDto(UserCategoryPreference preference) {
        UserCategoryPreferenceDTO dto = new UserCategoryPreferenceDTO();
        dto.setId(preference.getId());
        dto.setEnabled(preference.isEnabled());

        if (preference.getUser() != null) {
            dto.setUserId(preference.getUser().getUserId());
        }

        if (preference.getCategory() != null) {
            dto.setCategoryId(preference.getCategory().getCategoryId());
        }

        return dto;
    }

    public static UserCategoryPreference toEntity(UserCategoryPreferenceDTO dto) {
        UserCategoryPreference preference = new UserCategoryPreference();
        preference.setEnabled(dto.isEnabled());

        if (dto.getUserId() != null) {
            User user = new User();
            user.setUserId(dto.getUserId());
            preference.setUser(user);
        }

        if (dto.getCategoryId() != null) {
            Category category = new Category();
            category.setCategoryId(dto.getCategoryId());
            preference.setCategory(category);
        }

        return preference;
    }
}
