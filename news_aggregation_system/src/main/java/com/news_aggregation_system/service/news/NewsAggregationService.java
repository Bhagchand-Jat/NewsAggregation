package com.news_aggregation_system.service.news;

import com.news_aggregation_system.dto.ArticleDTO;
import com.news_aggregation_system.dto.ArticleFilterRequestDTO;
import com.news_aggregation_system.dto.ArticleReportDTO;
import com.news_aggregation_system.service.BaseService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface NewsAggregationService extends BaseService<ArticleDTO, Long> {

    List<ArticleDTO> getArticlesByCategory(String category);

    List<ArticleDTO> getArticlesByDate(Date date);

    List<ArticleDTO> getArticlesByDateRange(Date from, Date to);

    List<ArticleDTO> fetchExternalNews();

    List<ArticleDTO> searchArticlesByKeyword(String keyword);

    List<ArticleDTO> saveAllArticles(List<ArticleDTO> articleDTOs);

    List<ArticleDTO> getArticlesSortedByLikesAndDislikes();

    void updateArticleStatusById(Long articleId, boolean enabled);

    void reportArticle(ArticleReportDTO articleReportDTO);

    List<ArticleReportDTO> getAllArticleReportsByArticleId(Long articleId);

    Optional<ArticleReportDTO> getArticleReportByArticleIdAndUserId(Long articleId, Long userId);

    List<ArticleReportDTO> getAllArticlesReportsByUserId(Long userId);

    List<ArticleDTO> filterArticles(ArticleFilterRequestDTO articleFilterRequestDTO);
}
