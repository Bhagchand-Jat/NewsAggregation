package com.news_aggregation_system.mapper;

import com.news_aggregation_system.dto.ArticleReactionDTO;
import com.news_aggregation_system.model.Article;
import com.news_aggregation_system.model.ArticleReaction;
import com.news_aggregation_system.model.ReactionType;
import com.news_aggregation_system.model.User;

public class ArticleReactionMapper {
    public static ArticleReactionDTO toDto(ArticleReaction reaction) {
        ArticleReactionDTO dto = new ArticleReactionDTO();
        dto.setReactionId(reaction.getId());
        dto.setReactionType(reaction.getReactionType().name());
        dto.setArticleId(reaction.getArticle().getArticleId());
        dto.setUserId(reaction.getUser().getUserId());
        return dto;
    }

    public static ArticleReaction toEntity(ArticleReactionDTO dto, Article article, User user) {
        ArticleReaction reaction = new ArticleReaction();
        reaction.setId(dto.getReactionId());
        reaction.setReactionType(ReactionType.valueOf(dto.getReactionType()));
        reaction.setArticle(article);
        reaction.setUser(user);
        return reaction;
    }
}
