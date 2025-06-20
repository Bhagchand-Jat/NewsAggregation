package com.news_aggregation_system.repository;

import com.news_aggregation_system.model.Article;
import com.news_aggregation_system.model.ArticleReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArticleReportRepository extends JpaRepository<ArticleReport, Long> {
    long countByArticle(Article article);

    boolean existsByArticleArticleIdAndReportedByUserId(Long articleId, Long userId);
}

