package com.news_aggregation_system.mapper;

import com.news_aggregation_system.dto.ArticleDTO;
import com.news_aggregation_system.model.Article;

public class ArticleMapper {

    public static ArticleDTO toDto(Article article) {
        ArticleDTO dto = new ArticleDTO();
        dto.setArticleId(article.getArticleId());
        dto.setTitle(article.getTitle());
        dto.setContent(article.getContent());
        dto.setSource(article.getSource());
        dto.setUrl(article.getUrl());
        dto.setDescription(article.getDescription());
        dto.setEnabled(article.isEnabled());
        dto.setPublishedAt(article.getPublishedAt());

        if (article.getCategories() != null) {
            dto.setCategories(article.getCategories());
        }
        return dto;
    }

    public static Article toEntity(ArticleDTO dto) {
        Article article = new Article();
        article.setArticleId(dto.getArticleId());
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent() != null ? dto.getContent() : "No content available");
        article.setSource(dto.getSource());
        article.setUrl(dto.getUrl());
        article.setDescription(dto.getDescription() != null ? dto.getDescription() : "No description available");
        article.setPublishedAt(dto.getPublishedAt());
        article.setEnabled(dto.isEnabled());
        if (dto.getCategories() != null) {
            article.setCategories(dto.getCategories());
        }
        return article;
    }
}
