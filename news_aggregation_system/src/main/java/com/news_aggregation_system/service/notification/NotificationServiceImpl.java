package com.news_aggregation_system.service.notification;

import com.news_aggregation_system.dto.ArticleDTO;
import com.news_aggregation_system.dto.NotificationDTO;
import com.news_aggregation_system.mapper.NotificationMapper;
import com.news_aggregation_system.model.Notification;
import com.news_aggregation_system.model.User;
import com.news_aggregation_system.repository.NotificationRepository;
import com.news_aggregation_system.repository.UserRepository;
import com.news_aggregation_system.service.common.Constant;
import com.news_aggregation_system.service.user.CategoryPreferenceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.news_aggregation_system.service.common.Constant.*;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final CategoryPreferenceService categoryPreferenceService;
    private final EmailService emailService;

    public NotificationServiceImpl(NotificationRepository notificationRepository, UserRepository userRepository, CategoryPreferenceService categoryPreferenceService, EmailService emailService) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.categoryPreferenceService = categoryPreferenceService;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public List<NotificationDTO> getNotificationsByUserIdAndReadStatusAndUpdateMarkAsRead(Long userId, boolean isRead) {
        List<Notification> notifications = notificationRepository.findByUserUserIdAndViewed(userId, isRead);

        Date now = new Date();
        for (Notification notification : notifications) {
            notification.setViewed(true);
            notification.setReadAt(now);
        }
        notificationRepository.saveAll(notifications);

        return notifications.stream()
                .map(NotificationMapper::toDto)
                .collect(Collectors.toList());

    }

    @Override
    public void sendNotificationsForNewArticles(List<ArticleDTO> newArticles) {
        userRepository.findAll().forEach(user -> {

            if (Objects.equals(user.getRole().getRole(), Constant.USER_ROLE)) {
                Set<ArticleDTO> matchedArticles = getMatchedArticles(newArticles, user.getUserId());
                if (!matchedArticles.isEmpty()) {
                    createNotification(buildNotification(user, matchedArticles));
                    sendEmail(user.getEmail(), matchedArticles);
                }
            }

        });
    }


    private void createNotification(Notification notification) {
        notification = notificationRepository.save(notification);
        NotificationMapper.toDto(notification);
    }

    private Set<ArticleDTO> getMatchedArticles(List<ArticleDTO> articles,
                                               Long userId) {

        Set<String> userKeywords = extractUserKeywords(userId);

        return articles.stream()
                .filter(article -> containsKeyword(article, userKeywords))
                .collect(Collectors.toCollection(HashSet::new));
    }


    private Set<String> extractUserKeywords(Long userId) {
        return categoryPreferenceService.getEnabledKeywords(userId);
    }

    private boolean containsKeyword(ArticleDTO article, Set<String> keywords) {
        String title = article.getTitle().toLowerCase();
        String content = article.getContent().toLowerCase();
        return keywords.stream().anyMatch(keyword -> title.contains(keyword) || content.contains(keyword));
    }

    private Notification buildNotification(User user, Collection<ArticleDTO> matches) {
        StringBuilder body = new StringBuilder();
        matches.forEach(a -> body.append(DASH).append(a.getTitle()).append("\n")
                .append("  ").append(a.getUrl()).append("\n\n"));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(body.toString());
        notification.setViewed(false);
        notification.setCreatedAt(new Date());
        return notification;
    }

    private void sendEmail(String to, Collection<ArticleDTO> matches) {
        String subject = "📰 " + matches.size() + NEW_ARTICLES_FOR_YOU;
        String body = MATCHING_ARTICLES +
                matches.stream()
                        .map(article -> DASH + article.getTitle() + "\n  " + article.getUrl())
                        .collect(Collectors.joining("\n\n"))
                +
                NEWS_AGGREGATOR;
        emailService.sendEmail(to, subject, body);
    }

    @Override
    public void deleteByIsViewedTrueAndReadAtBefore(Date date) {
        notificationRepository.deleteByViewedTrueAndReadAtBefore(date);

    }


}
