package com.news_aggregation_system.service.news;

import com.news_aggregation_system.dto.ArticleDTO;
import com.news_aggregation_system.dto.ArticleFilterRequestDTO;
import com.news_aggregation_system.dto.ArticleReportDTO;
import com.news_aggregation_system.service.BaseService;

import java.util.Date;
import java.util.List;

public interface NewsAggregationService extends BaseService<ArticleDTO, Long> {


    List<ArticleDTO> fetchExternalNews();

    List<ArticleDTO> saveAllArticles(List<ArticleDTO> articleDTOs);

    void updateArticleStatusById(Long articleId, boolean enabled);

    void reportArticle(ArticleReportDTO articleReportDTO);

    List<ArticleDTO> getAllReportedArticles();

    List<ArticleReportDTO> getAllArticleReportsByArticleId(Long articleId);

    List<ArticleReportDTO> getAllArticlesReportsByUserId(Long userId);

    List<ArticleDTO> filterArticles(Long userId, ArticleFilterRequestDTO articleFilterRequestDTO);
}
