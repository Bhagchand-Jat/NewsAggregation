package com.news_aggregation_system.mapper;

import com.news_aggregation_system.dto.UserKeywordPreferenceDTO;
import com.news_aggregation_system.model.Category;
import com.news_aggregation_system.model.User;
import com.news_aggregation_system.model.UserKeywordPreference;

public class UserKeywordPreferenceMapper {

    public static UserKeywordPreferenceDTO toDTO(UserKeywordPreference userKeywordPreference) {
        UserKeywordPreferenceDTO userKeywordPreferenceDTO = new UserKeywordPreferenceDTO();
        userKeywordPreferenceDTO.setId(userKeywordPreference.getId());
        if (userKeywordPreference.getCategory() != null) {
            userKeywordPreferenceDTO.setCategoryId(userKeywordPreference.getCategory().getCategoryId());
        }
        userKeywordPreferenceDTO.setKeyword(userKeywordPreference.getKeyword());
        if (userKeywordPreference.getUser() != null) {
            userKeywordPreferenceDTO.setUserId(userKeywordPreference.getUser().getUserId());
        }


        return userKeywordPreferenceDTO;
    }

    public static UserKeywordPreference toEntity(UserKeywordPreferenceDTO userKeywordPreferenceDTO) {
        UserKeywordPreference userKeywordPreference = new UserKeywordPreference();
        userKeywordPreference.setId(userKeywordPreferenceDTO.getId());
        userKeywordPreference.setKeyword(userKeywordPreferenceDTO.getKeyword());
        Category category = new Category();
        category.setCategoryId(userKeywordPreferenceDTO.getCategoryId());
        userKeywordPreference.setCategory(category);
        User user = new User();
        user.setUserId(userKeywordPreferenceDTO.getUserId());
        userKeywordPreference.setUser(user);
        return userKeywordPreference;
    }
}
