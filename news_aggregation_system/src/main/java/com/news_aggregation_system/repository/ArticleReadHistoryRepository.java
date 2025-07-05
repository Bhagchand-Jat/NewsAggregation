package com.news_aggregation_system.repository;

import com.news_aggregation_system.model.ArticleReadHistory;
import com.news_aggregation_system.model.Category;
import com.news_aggregation_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ArticleReadHistoryRepository extends JpaRepository<ArticleReadHistory, Long> {
    boolean existsByUserUserIdAndArticleArticleId(Long userId, Long articleId);

    List<ArticleReadHistory> findByUserUserId(Long userId);

    @Query("""
                select distinct c
                from ArticleReadHistory h
                     join h.article a
                     join a.categories c
                where h.user.userId = :userId
            """)
    Set<Category> categoriesReadByUser(@Param("userId") Long userId);

    @Query("select h.article.articleId from ArticleReadHistory h where h.user.userId = :userId")
    Set<Long> idsOfReadArticles(@Param("userId") Long userId);

    Long user(User user);
}
