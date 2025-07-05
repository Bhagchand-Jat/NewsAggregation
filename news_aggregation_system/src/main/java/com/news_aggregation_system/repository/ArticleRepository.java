package com.news_aggregation_system.repository;

import com.news_aggregation_system.model.Article;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {
    @Query("""
                SELECT a FROM Article a
                JOIN a.categories c
                WHERE LOWER(c.name) = LOWER(:categoryName)
            """)
    List<Article> findByCategoryName(@Param("categoryName") String categoryName);


    @Query("SELECT a FROM Article a WHERE DATE(a.publishedAt) = :publishedAt")
    List<Article> findByPublishedAtDate(@Param("publishedAt") Date publishedAt);

    @Query("SELECT a FROM Article a WHERE a.publishedAt BETWEEN :start AND :end")
    List<Article> findAllByPublishedAtBetween(@Param("start") Date start, @Param("end") Date end, Sort sort);

    @Query("""
            SELECT a FROM Article a
            LEFT JOIN a.reactions r
            GROUP BY a
            ORDER BY
                SUM(CASE WHEN r.reactionType = 'LIKE' THEN 1 ELSE 0 END) DESC,
                SUM(CASE WHEN r.reactionType = 'DISLIKE' THEN 1 ELSE 0 END) DESC
            """)
    List<Article> findAllOrderByLikesAndDislikes();

    List<Article> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String titleKeyword,
                                                                               String contentKeyword);

    @Modifying
    @Query("UPDATE Article a SET a.enabled = :enabled WHERE a.articleId = :articleId")
    int updateArticleStatusById(@Param("articleId") Long articleId, @Param("enabled") boolean enabled);

    @Query("""
               SELECT a
               FROM Article a
               WHERE a.enabled = true
                 AND (SELECT COUNT(r) FROM ArticleReport r WHERE r.article = a) >= :threshold
            """)
    List<Article> findMaxThresholdAndEnabledArticles(@Param("threshold") int threshold);

    boolean existsByTitle(String title);
}
