package com.news_aggregation_system.converter.external;

import com.news_aggregation_system.dto.ArticleDTO;

import java.util.List;

public interface ExternalArticleConverter {

    boolean supports(String url);

    List<ArticleDTO> convert(String json) throws Exception;

}
