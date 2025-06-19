package com.news_aggregation_system.mapper;

import com.news_aggregation_system.dto.SavedArticleDTO;
import com.news_aggregation_system.model.Article;
import com.news_aggregation_system.model.SavedArticle;
import com.news_aggregation_system.model.User;

public class SavedArticleMapper {

    public static SavedArticleDTO toDTO(SavedArticle entity) {
        SavedArticleDTO dto = new SavedArticleDTO();
        dto.setUserId(entity.getUser().getUserId());
        dto.setArticleId(entity.getArticle().getArticleId());
        dto.setSavedAt(entity.getSavedAt());
        return dto;
    }

    public static SavedArticle toEntity(SavedArticleDTO dto, User user, Article article) {
        SavedArticle entity = new SavedArticle();
        entity.setUser(user);
        entity.setArticle(article);
        entity.setSavedAt(dto.getSavedAt());
        return entity;
    }
}
