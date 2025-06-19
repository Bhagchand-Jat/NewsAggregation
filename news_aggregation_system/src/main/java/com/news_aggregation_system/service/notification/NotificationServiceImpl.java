package com.news_aggregation_system.service.notification;

import com.news_aggregation_system.dto.ArticleDTO;
import com.news_aggregation_system.dto.NotificationDTO;
import com.news_aggregation_system.mapper.NotificationMapper;
import com.news_aggregation_system.model.Notification;
import com.news_aggregation_system.model.NotificationConfig;
import com.news_aggregation_system.model.User;
import com.news_aggregation_system.model.UserCategoryPreference;
import com.news_aggregation_system.repository.NotificationConfigRepository;
import com.news_aggregation_system.repository.NotificationRepository;
import com.news_aggregation_system.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationConfigRepository notificationConfigRepository;
    private final EmailService emailService;

    public NotificationServiceImpl(NotificationRepository notificationRepository, UserRepository userRepository,
                                   NotificationConfigRepository notificationConfigRepository, EmailService emailService) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.notificationConfigRepository = notificationConfigRepository;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public List<NotificationDTO> getNotificationsByUserIdAndReadStatusAndUpdateMarkAsRead(Long userId, boolean isRead) {
        List<Notification> notifications = notificationRepository.findByUserUserIdAndRead(userId, isRead);

        Date now = new Date();
        for (Notification notification : notifications) {
            notification.setRead(true);
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
            Optional<NotificationConfig> notificationConfig = notificationConfigRepository.findByUser(user);
            if (notificationConfig.isPresent()) {
                Set<ArticleDTO> matchedArticles = getMatchedArticles(newArticles, notificationConfig.get());
                if (!matchedArticles.isEmpty()) {
                    createNotification(buildNotification(user, matchedArticles));
                    sendEmail(user.getEmail(), matchedArticles);
                }
            }

        });
    }

    @Override
    public NotificationDTO createNotification(Notification notification) {
        notification = notificationRepository.save(notification);
        return NotificationMapper.toDto(notification);
    }

    private Set<ArticleDTO> getMatchedArticles(List<ArticleDTO> articles, NotificationConfig config) {

        // Step 1: Get user's enabled category preferences
        Set<String> enabledCategories = config.getCategoryPreferences().stream()
                .filter(UserCategoryPreference::isEnabled)
                .map(pref -> pref.getCategory().getName().toLowerCase())
                .collect(Collectors.toSet());

        // Step 2: Match articles with any of the enabled categories
        Set<ArticleDTO> categoryMatched = articles.stream()
                .filter(article -> article.getCategories().stream()
                        .map(c -> c.getName().toLowerCase())
                        .anyMatch(enabledCategories::contains))
                .collect(Collectors.toSet());

        Set<ArticleDTO> matchedArticles = new HashSet<>(categoryMatched);

        // Step 3: Match remaining articles (not matched by category) with keywords
        Set<ArticleDTO> remainingArticles = new HashSet<>(articles);
        remainingArticles.removeAll(categoryMatched);

        if (config.isKeywordsEnabled() && !remainingArticles.isEmpty()) {
            Set<String> userKeywords = config.getUser().getKeywords().stream()
                    .map(k -> k.getName().toLowerCase())
                    .collect(Collectors.toSet());

            Set<ArticleDTO> keywordMatched = remainingArticles.stream()
                    .filter(article -> containsKeyword(article, userKeywords))
                    .collect(Collectors.toSet());

            matchedArticles.addAll(keywordMatched);
        }

        return matchedArticles;
    }

    private boolean containsKeyword(ArticleDTO article, Set<String> keywords) {
        String title = article.getTitle().toLowerCase();
        String content = article.getContent().toLowerCase();
        return keywords.stream().anyMatch(kw -> title.contains(kw) || content.contains(kw));
    }

    private Notification buildNotification(User user, Collection<ArticleDTO> matches) {
        StringBuilder body = new StringBuilder();
        matches.forEach(a -> body.append("- ").append(a.getTitle()).append("\n")
                .append("  ").append(a.getUrl()).append("\n\n"));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(body.toString());
        notification.setRead(false);
        notification.setCreatedAt(new Date());
        return notification;
    }

    private void sendEmail(String to, Collection<ArticleDTO> matches) {
        String subject = "📰 " + matches.size() + " new article(s) for you";
        String body = "Hi,\n\nHere are matching articles:\n\n" +
                matches.stream()
                        .map(a -> "- " + a.getTitle() + "\n  " + a.getUrl())
                        .collect(Collectors.joining("\n\n"))
                +
                "\n\n— News Aggregator";
        emailService.sendEmail(to, subject, body);
    }

    @Override
    public void deleteByIsReadTrueAndReadAtBefore(Date date) {
        notificationRepository.deleteByReadTrueAndReadAtBefore(date);
    }
}
