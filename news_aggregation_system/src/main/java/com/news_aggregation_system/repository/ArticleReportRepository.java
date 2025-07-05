package com.news_aggregation_system.repository;

import com.news_aggregation_system.model.Article;
import com.news_aggregation_system.model.ArticleReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArticleReportRepository extends JpaRepository<ArticleReport, Long> {

    boolean existsByArticleArticleIdAndReportedByUserId(Long articleId, Long userId);

    long countByArticleArticleId(Long articleArticleId);

    List<ArticleReport> findArticleReportByArticleArticleId(Long articleId);


    Optional<ArticleReport> findArticleReportByArticleArticleIdAndReportedByUserId(Long articleId, Long userId);

    List<ArticleReport> findArticleReportByReportedByUserId(Long userId);

    @Query("""
                select distinct r.article
                from ArticleReport r
            """)
    List<Article> getAllReportedArticles();
}

