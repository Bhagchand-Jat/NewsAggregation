package com.news_aggregation_system.service.user;

import com.news_aggregation_system.dto.ArticleReactionDTO;
import com.news_aggregation_system.exception.NotFoundException;
import com.news_aggregation_system.mapper.ArticleReactionMapper;
import com.news_aggregation_system.model.Article;
import com.news_aggregation_system.model.ArticleReaction;
import com.news_aggregation_system.model.ReactionType;
import com.news_aggregation_system.model.User;
import com.news_aggregation_system.repository.ArticleReactionRepository;
import com.news_aggregation_system.repository.ArticleRepository;
import com.news_aggregation_system.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static com.news_aggregation_system.service.common.Constant.*;

@Service
public class ArticleReactionServiceImpl implements ArticleReactionService {
    private final ArticleReactionRepository reactionRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;


    public ArticleReactionServiceImpl(ArticleReactionRepository reactionRepository, ArticleRepository articleRepository,
                                      UserRepository userRepository) {
        this.reactionRepository = reactionRepository;
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ArticleReactionDTO reactToArticle(ArticleReactionDTO dto) {
        Article article = articleRepository.findById(dto.getArticleId())
                .orElseThrow(() -> new NotFoundException(ARTICLE, ID + dto.getArticleId()));
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NotFoundException(USER, ID + dto.getUserId()));

        Optional<ArticleReaction> existing = reactionRepository.findByUserUserIdAndArticleArticleId(user.getUserId(), article.getArticleId());

        ArticleReaction reaction;
        if (existing.isPresent()) {
            reaction = existing.get();
            reaction.setReactionType(ReactionType.valueOf(dto.getReactionType()));
        } else {
            reaction = ArticleReactionMapper.toEntity(dto, article, user);
        }

        return ArticleReactionMapper.toDto(reactionRepository.save(reaction));
    }

    @Override
    public Set<Long> getArticleIdsLikedByUser(Long userId) {
        return reactionRepository.findArticleIdsLikedByUserUserId(userId);
    }
}
