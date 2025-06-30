package com.news_aggregation_system.repository;

import com.news_aggregation_system.model.SavedArticle;
import com.news_aggregation_system.model.SavedArticleId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavedArticleRepository extends JpaRepository<SavedArticle, SavedArticleId> {
    List<SavedArticle> findByUserUserId(Long userId);

    boolean existsSavedArticleByArticleArticleIdAndUserUserId(Long articleId, Long userId);
}
