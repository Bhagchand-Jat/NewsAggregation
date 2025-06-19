package com.news_aggregation_system.service.news;

import java.util.List;

import com.news_aggregation_system.dto.ArticleDTO;

public interface ExternalNewsFetcherService {
List<ArticleDTO> fetchAndConvert(String url);
}
