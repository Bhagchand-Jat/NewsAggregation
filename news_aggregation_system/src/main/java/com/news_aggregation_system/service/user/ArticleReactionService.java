package com.news_aggregation_system.service.user;

import com.news_aggregation_system.dto.ArticleReactionDTO;

public interface ArticleReactionService {
    ArticleReactionDTO reactToArticle(ArticleReactionDTO dto);

    long countLikes(Long articleId);

    long countDislikes(Long articleId);
}
