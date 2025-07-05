package com.news_aggregation_system.repository;

import com.news_aggregation_system.model.ArticleReaction;
import com.news_aggregation_system.model.ReactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface ArticleReactionRepository extends JpaRepository<ArticleReaction, Long> {

    Optional<ArticleReaction> findByUserUserIdAndArticleArticleId(Long userId, Long articleId);

    long countByArticleArticleIdAndReactionType(Long articleId, ReactionType reactionType);

    @Query("""
              select ar.article.articleId from ArticleReaction  ar
              where  ar.user.userId =:userId and ar.reactionType = 'LIKE'
            """)
    Set<Long> findArticleIdsLikedByUserUserId(Long userId);
}

