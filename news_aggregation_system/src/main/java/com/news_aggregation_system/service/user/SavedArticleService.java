package com.news_aggregation_system.service.user;

import com.news_aggregation_system.dto.ArticleDTO;
import com.news_aggregation_system.dto.SavedArticleDTO;

import java.util.List;

public interface SavedArticleService {

    List<ArticleDTO> getSavedArticlesByUser(Long userId);

    void saveArticle(SavedArticleDTO savedArticleDTO);

    void deleteSavedArticle(Long userId, Long articleId);
}
