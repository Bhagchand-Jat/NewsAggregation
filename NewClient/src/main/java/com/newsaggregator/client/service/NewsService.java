package com.newsaggregator.client.service;

import com.newsaggregator.client.dto.ArticleDTO;
import com.newsaggregator.client.dto.ArticleReadHistoryDTO;
import com.newsaggregator.client.dto.ArticleReportDTO;
import com.newsaggregator.client.dto.CategoryDTO;

import java.util.Date;
import java.util.List;

public interface NewsService {
    List<ArticleDTO> fetchHeadlines(Date from, Date to, Long categoryId);

    void saveArticle(Long articleId);

    boolean deleteSavedArticle(Long articleId);

    List<ArticleDTO> getSavedArticles();

    List<ArticleDTO> searchArticles(String query);

    List<ArticleDTO> allNewsArticles();

    List<ArticleDTO> todayNewsArticles();

    void reportArticle(Long articleId, String reason);

    void likeArticle(Long articleId);

    void disLikeArticle(Long articleId);

    List<CategoryDTO> getCategories();

    List<ArticleReportDTO> getUserReports();

    void markArticleAsRead(Long articleId);

    List<ArticleReadHistoryDTO> getArticlesReadHistory();
}
