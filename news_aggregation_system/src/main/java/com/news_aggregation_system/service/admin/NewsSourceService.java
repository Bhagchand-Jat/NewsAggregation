package com.news_aggregation_system.service.admin;

import com.news_aggregation_system.dto.NewsSourceDTO;
import com.news_aggregation_system.service.BaseService;

import java.util.List;

public interface NewsSourceService extends BaseService<NewsSourceDTO, Long> {
    List<NewsSourceDTO> findAllByEnabled();
    List<NewsSourceDTO > getAllByEnabledAndUpdateLastModified();
}
