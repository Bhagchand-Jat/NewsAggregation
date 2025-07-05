package com.news_aggregation_system.repository;

import com.news_aggregation_system.model.SavedArticle;
import com.news_aggregation_system.model.SavedArticleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface SavedArticleRepository extends JpaRepository<SavedArticle, SavedArticleId> {
    List<SavedArticle> findByUserUserId(Long userId);

    boolean existsSavedArticleByArticleArticleIdAndUserUserId(Long articleId, Long userId);

    @Query("""
            select  sa.article.articleId from SavedArticle sa
            where sa.user.userId = :userId
            """)
    Set<Long> findArticleIdsSavedByUser(@Param("userId") Long userId);
}
