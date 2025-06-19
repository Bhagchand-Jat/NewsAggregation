package com.news_aggregation_system.mapper;

import com.news_aggregation_system.dto.NewsSourceDTO;
import com.news_aggregation_system.model.NewsSource;

public class NewsSourceMapper {

    public static NewsSourceDTO toDto(NewsSource source) {
        NewsSourceDTO dto = new NewsSourceDTO();
        dto.setSourceId(source.getSourceId());
        dto.setSourceUrl(source.getSourceUrl());
        dto.setSourceApiKey(source.getSourceApiKey());
        dto.setEnabled(source.isEnabled());
        return dto;
    }

    public static NewsSource toEntity(NewsSourceDTO dto) {
        NewsSource source = new NewsSource();
        source.setSourceUrl(dto.getSourceUrl());
        source.setSourceApiKey(dto.getSourceApiKey());
         source.setEnabled(dto.isEnabled());
        return source;
    }
}
