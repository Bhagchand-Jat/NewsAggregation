package com.news_aggregation_system.scheduler;

import com.news_aggregation_system.dto.ArticleDTO;
import com.news_aggregation_system.service.news.NewsAggregationService;
import com.news_aggregation_system.service.notification.NotificationService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class NewsFetchScheduler {
 private final Logger logger = LoggerFactory.getLogger(NewsFetchScheduler.class);
    private final NewsAggregationService newsAggregationService;

    private final NotificationService notificationService;

    public NewsFetchScheduler(NewsAggregationService newsAggregationService, NotificationService notificationService) {
        this.newsAggregationService = newsAggregationService;
        this.notificationService = notificationService;
    }

        @Scheduled(cron = "0 0 0/3 * * ?")
//@Scheduled(cron = "0 * * * * ?")
    public void fetchArticlesFromSourcesAndSaveToDatabaseAndSendNotificationToUsers()
            {

        List<ArticleDTO> articles = newsAggregationService.fetchExternalNews();
        List<ArticleDTO> articleDTOS = newsAggregationService.saveAllArticles(articles);
        notificationService.sendNotificationsForNewArticles(articleDTOS);

    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void deleteOldReadNotifications() {
        Date sevenDaysAgo = new Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000L);
        notificationService.deleteByIsReadTrueAndReadAtBefore(sevenDaysAgo);
    }

}
