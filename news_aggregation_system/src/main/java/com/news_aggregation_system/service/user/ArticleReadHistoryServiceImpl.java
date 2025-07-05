package com.news_aggregation_system.service.user;

import com.news_aggregation_system.model.Article;
import com.news_aggregation_system.model.ArticleReadHistory;
import com.news_aggregation_system.model.User;
import com.news_aggregation_system.repository.ArticleReadHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ArticleReadHistoryServiceImpl implements ArticleReadHistoryService {

    private final ArticleReadHistoryRepository articleReadHistoryRepository;

    public ArticleReadHistoryServiceImpl(ArticleReadHistoryRepository articleReadHistoryRepository) {
        this.articleReadHistoryRepository = articleReadHistoryRepository;
    }

    @Override
    public void markAsRead(Long userId, Long articleId) {
        boolean isExists = articleReadHistoryRepository.existsByUserUserIdAndArticleArticleId(userId, articleId);
        if (!isExists) {
            ArticleReadHistory articleReadHistory = new ArticleReadHistory();
            Article article = new Article();
            article.setArticleId(articleId);
            articleReadHistory.setArticle(article);
            User user = new User();
            user.setUserId(userId);
            articleReadHistory.setUser(user);
            articleReadHistory.setReadAt(new Date(System.currentTimeMillis()));

            articleReadHistoryRepository.save(articleReadHistory);
        }

    }
}
