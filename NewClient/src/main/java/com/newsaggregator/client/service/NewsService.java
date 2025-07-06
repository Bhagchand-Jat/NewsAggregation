package com.newsaggregator.client.service;

import com.newsaggregator.client.dto.ArticleDTO;
import com.newsaggregator.client.dto.ArticleReadHistoryDTO;
import com.newsaggregator.client.dto.ArticleReportDTO;
import com.newsaggregator.client.dto.CategoryDTO;

import java.util.Date;
import java.util.List;

public interface NewsService {
    List<ArticleDTO> fetchHeadlines(Date from, Date to, Long categoryId,Long userId);

    void saveArticle(Long userId, Long articleId);

    boolean deleteSavedArticle(Long userId, Long articleId);

    List<ArticleDTO> getSavedArticles(Long userId);

    List<ArticleDTO> searchArticles(String query,Long userId);

    List<ArticleDTO> allNewsArticles();

    List<ArticleDTO> todayNewsArticles(Long userId);

    void reportArticle(Long userId, Long articleId, String reason);

    void likeArticle(Long articleId, Long userId);

    void disLikeArticle(Long articleId, Long userId);

    List<CategoryDTO> getCategories();

    List<ArticleReportDTO> getUserReports(Long userId);

    void markArticleAsRead(Long userId, Long articleId);

    List<ArticleReadHistoryDTO> getArticlesReadHistory(Long userId);
}
