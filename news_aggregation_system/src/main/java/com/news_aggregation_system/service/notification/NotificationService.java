package com.news_aggregation_system.service.notification;

import com.news_aggregation_system.dto.ArticleDTO;
import com.news_aggregation_system.dto.NotificationDTO;

import java.util.Date;
import java.util.List;

public interface NotificationService {
    List<NotificationDTO> getNotificationsByUserIdAndReadStatusAndUpdateMarkAsRead(Long userId, boolean isRead);

    void sendNotificationsForNewArticles(List<ArticleDTO> newArticles);


    void deleteByIsViewedTrueAndReadAtBefore(Date date);


}
