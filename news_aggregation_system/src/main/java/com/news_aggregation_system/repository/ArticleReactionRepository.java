package com.news_aggregation_system.repository;

import com.news_aggregation_system.model.Article;
import com.news_aggregation_system.model.ArticleReaction;
import com.news_aggregation_system.model.ReactionType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleReactionRepository extends JpaRepository<ArticleReaction, Long> {

    List<ArticleReaction> findByUser_UserIdAndReactionType(Long userId, ReactionType type);

    @Query("""
            SELECT a
            FROM Article a
            LEFT JOIN a.reactions r
                WITH r.reactionType = :type
            GROUP BY a
            ORDER BY COUNT(r) DESC
            """)
    List<Article> findMostReacted(@Param("type") ReactionType type, Pageable pageable);

    Optional<ArticleReaction> findByUserUserIdAndArticleArticleId(Long userId, Long articleId);

    long countByArticleArticleIdAndReactionType(Long articleId, ReactionType reactionType);
}

