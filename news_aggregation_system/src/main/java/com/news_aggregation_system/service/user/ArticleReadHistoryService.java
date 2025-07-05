package com.news_aggregation_system.service.user;

import com.news_aggregation_system.dto.ArticleReadHistoryDTO;

import java.util.List;
import java.util.Set;

public interface ArticleReadHistoryService {
    void markAsRead(Long userId, Long articleId);

    List<ArticleReadHistoryDTO> getArticleReadHistory(Long userId);

    Set<Long> getArticleIdsReadByUser(Long userId);
}
