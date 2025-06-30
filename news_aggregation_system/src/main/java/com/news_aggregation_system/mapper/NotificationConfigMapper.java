package com.news_aggregation_system.mapper;

import com.news_aggregation_system.dto.NotificationConfigDTO;
import com.news_aggregation_system.model.NotificationConfig;
import com.news_aggregation_system.model.User;
import com.news_aggregation_system.model.UserCategoryPreference;

import java.util.Set;
import java.util.stream.Collectors;

public class NotificationConfigMapper {

    public static NotificationConfigDTO toDto(NotificationConfig config) {
        NotificationConfigDTO dto = new NotificationConfigDTO();
        dto.setId(config.getId());
        dto.setKeywordsEnabled(config.isKeywordsEnabled());
        if (config.getUser() != null) {
            dto.setUserId(config.getUser().getUserId());
        }
        dto.setUserCategoryPreferences(config.getCategoryPreferences().stream().map(UserCategoryPreferenceMapper::toDto).collect(Collectors.toSet()));
        return dto;
    }

    public static NotificationConfig toEntity(NotificationConfigDTO dto) {
        NotificationConfig config = new NotificationConfig();
        config.setKeywordsEnabled(dto.isKeywordsEnabled());

        if (dto.getUserId() != null) {
            User user = new User();
            user.setUserId(dto.getUserId());
            config.setUser(user);
        }
        Set<UserCategoryPreference> preferences = dto.getUserCategoryPreferences().stream().map(UserCategoryPreferenceMapper::toEntity).collect(Collectors.toSet());
        config.setCategoryPreferences(preferences);

        return config;
    }
}
