package com.news_aggregation_system.mapper;

import com.news_aggregation_system.dto.KeywordDTO;
import com.news_aggregation_system.model.Keyword;

public class KeywordMapper {

    public static KeywordDTO toDto(Keyword keyword) {
        KeywordDTO dto = new KeywordDTO();
        dto.setKeywordId(keyword.getKeywordId());
        dto.setName(keyword.getName());
        dto.setEnabled(keyword.isEnabled());


        return dto;
    }

    public static Keyword toEntity(KeywordDTO dto) {
        Keyword keyword = new Keyword();
        keyword.setKeywordId(dto.getKeywordId());
        keyword.setEnabled(dto.isEnabled());
        keyword.setName(dto.getName());


        return keyword;
    }
}
