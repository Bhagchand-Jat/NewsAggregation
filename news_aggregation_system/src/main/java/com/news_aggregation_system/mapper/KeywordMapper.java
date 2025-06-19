package com.news_aggregation_system.mapper;

import com.news_aggregation_system.dto.KeywordDTO;
import com.news_aggregation_system.model.Keyword;
import com.news_aggregation_system.model.User;

public class KeywordMapper {

    public static KeywordDTO toDto(Keyword keyword) {
        KeywordDTO dto = new KeywordDTO();
        dto.setKeywordId(keyword.getKeywordId());
        dto.setName(keyword.getName());
        if (keyword.getUser() != null) {
            dto.setUserId(keyword.getUser().getUserId());
        }

        return dto;
    }

    public static Keyword toEntity(KeywordDTO dto) {
        Keyword keyword = new Keyword();
        keyword.setName(dto.getName());
        if (dto.getUserId() != null) {
            User user = new User();
            user.setUserId(dto.getUserId());
            keyword.setUser(user);
        }

        return keyword;
    }
}
