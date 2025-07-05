package com.news_aggregation_system.service.user;

import com.news_aggregation_system.dto.ArticleReadHistoryDTO;
import com.news_aggregation_system.mapper.ArticleReadHistoryMapper;
import com.news_aggregation_system.repository.ArticleReadHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
            articleReadHistoryRepository.save(ArticleReadHistoryMapper.toEntity(articleId, userId));
        }

    }

    @Override
    public List<ArticleReadHistoryDTO> getArticleReadHistory(Long userId) {
        return articleReadHistoryRepository.findByUserUserId(userId).stream().map(ArticleReadHistoryMapper::toDTO).collect(Collectors.toList());
    }
}
