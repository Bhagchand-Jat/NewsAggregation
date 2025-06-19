package com.news_aggregation_system.service.user;

import com.news_aggregation_system.dto.ArticleDTO;

import java.util.List;

public interface SavedArticleService {

    List<ArticleDTO> getSavedArticlesByUser(Long userId);

    void saveArticle(Long userId, Long articleId);

    void deleteSavedArticle(Long userId, Long articleId);
}
