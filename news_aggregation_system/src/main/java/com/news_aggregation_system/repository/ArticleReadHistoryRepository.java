package com.news_aggregation_system.repository;

import com.news_aggregation_system.model.ArticleReadHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ArticleReadHistoryRepository extends JpaRepository<ArticleReadHistory, Long> {
    boolean existsByUserUserIdAndArticleArticleId(Long userId, Long articleId);

    List<ArticleReadHistory> findByUserUserId(Long userId);

    @Query("select h.article.articleId from ArticleReadHistory h where h.user.userId = :userId")
    Set<Long> findArticleIdReadByUser(@Param("userId") Long userId);

}
