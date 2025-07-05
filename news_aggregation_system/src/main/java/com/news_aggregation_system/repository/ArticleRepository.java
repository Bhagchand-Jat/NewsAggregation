package com.news_aggregation_system.repository;

import com.news_aggregation_system.model.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("""
                SELECT a FROM Article a
                JOIN a.categories c
                WHERE LOWER(c.name) = LOWER(:categoryName)
            """)
    List<Article> findByCategoryName(@Param("categoryName") String categoryName);


    @Query("SELECT a FROM Article a WHERE DATE(a.publishedAt) = :publishedAt")
    List<Article> findByPublishedAtDate(@Param("publishedAt") Date publishedAt);

    @Query("SELECT a FROM Article a WHERE a.publishedAt BETWEEN :start AND :end")
    List<Article> findAllByPublishedAtBetween(@Param("start") Date start, @Param("end") Date end);

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
                 JOIN a.categories c
            WHERE c.categoryId = :categoryId
              AND a.enabled = true
            ORDER BY a.likeCount DESC, a.dislikeCount ASC
            """)
    List<Article> getArticlesByCategoryId(@Param("categoryId") Long categoryId);

    @Query("""
                SELECT a
                FROM Article a
                     JOIN a.categories c
                WHERE c.categoryId = :categoryId
                  AND a.publishedAt BETWEEN :from AND :to
                  AND a.enabled = true
                ORDER BY a.likeCount DESC, a.dislikeCount ASC
            """)
    List<Article> getArticlesByDateRangeAndCategory(@Param("from") Date from,
                                                    @Param("to") Date to,
                                                    @Param("categoryId") Long categoryId);

    @Query("""
            select distinct a from Article a
            where a.articleId not in :alreadyRead
              and (
                  lower(a.title)       like %:kw%
               or lower(a.description) like %:kw%
               or lower(a.content)     like %:kw%
              )
            order by a.publishedAt desc
            """)
    List<Article> searchByKeyword(@Param("kw") String keyword,
                                  @Param("alreadyRead") Set<Long> alreadyRead,
                                  Pageable pageable);

    @Query("""
               SELECT a
               FROM Article a
               WHERE a.enabled = true
                 AND (SELECT COUNT(r) FROM ArticleReport r WHERE r.article = a) >= :threshold
            """)
    List<Article> findMaxThresholdAndEnabledArticles(@Param("threshold") int threshold);

}
