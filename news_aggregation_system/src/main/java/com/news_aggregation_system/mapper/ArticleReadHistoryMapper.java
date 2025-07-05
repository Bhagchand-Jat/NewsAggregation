package com.news_aggregation_system.mapper;

import com.news_aggregation_system.dto.ArticleReadHistoryDTO;
import com.news_aggregation_system.model.Article;
import com.news_aggregation_system.model.ArticleReadHistory;
import com.news_aggregation_system.model.User;

import java.util.Date;

public class ArticleReadHistoryMapper {
    public static ArticleReadHistoryDTO toDTO(ArticleReadHistory articleReadHistory) {
        ArticleReadHistoryDTO articleReadHistoryDTO = new ArticleReadHistoryDTO();
        articleReadHistoryDTO.setArticleTitle(articleReadHistory.getArticle().getTitle());
        articleReadHistoryDTO.setReadAt(articleReadHistory.getReadAt());
        return articleReadHistoryDTO;
    }

    public static ArticleReadHistory toEntity(Long articleId, Long userId) {
        ArticleReadHistory articleReadHistory = new ArticleReadHistory();
        Article article = new Article();
        article.setArticleId(articleId);
        articleReadHistory.setArticle(article);
        User user = new User();
        user.setUserId(userId);
        articleReadHistory.setUser(user);
        articleReadHistory.setReadAt(new Date(System.currentTimeMillis()));
        return articleReadHistory;
    }
}
