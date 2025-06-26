package com.news_aggregation_system.service.news;

import com.news_aggregation_system.dto.ArticleDTO;

import java.util.List;

public interface ExternalNewsFetcherService {
    List<ArticleDTO> fetchAndConvert(String url, String apiKey);
}
