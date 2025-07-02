package com.news_aggregation_system.service.user;

public interface ArticleReadHistoryService {
    void markAsRead(Long userId, Long articleId);
}
