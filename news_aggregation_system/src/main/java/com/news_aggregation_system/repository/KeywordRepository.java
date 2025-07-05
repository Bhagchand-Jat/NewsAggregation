package com.news_aggregation_system.repository;

import com.news_aggregation_system.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface KeywordRepository extends JpaRepository<Keyword, Long> {


    List<Keyword> getKeywordsByCategoryCategoryId(Long categoryId);

    boolean existsByCategoryCategoryIdAndNameContainsIgnoreCase(Long categoryId, String word);

    int deleteKeywordByCategoryCategoryIdAndKeywordId(Long categoryId, Long keywordId);

    int deleteKeywordByCategoryCategoryIdAndNameContainingIgnoreCase(Long categoryId, String keywordName);

    @Query("update Keyword k set k.enabled=:enabled where k.category.categoryId =:categoryId and k.keywordId = :keywordId")
    @Modifying
    void updateEnabledByCategoryCategoryIdAndKeywordId(boolean enabled, Long categoryId, Long keywordId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("""
             update Keyword k
                set k.enabled = :enabled
              where k.category.categoryId = :categoryId
                and lower(k.name) = lower(:keywordName)
            """)
    int updateEnabledByCategoryCategoryIdAndNameContainingIgnoreCase(boolean enabled, Long categoryId, String keywordName);
}
