package com.news_aggregation_system.service.user;

import com.news_aggregation_system.dto.ArticleReactionDTO;

import java.util.Set;

public interface ArticleReactionService {
    ArticleReactionDTO reactToArticle(ArticleReactionDTO dto);

    Set<Long> getArticleIdsLikedByUser(Long userId);
}
