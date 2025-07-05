package com.news_aggregation_system.repository;

import com.news_aggregation_system.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface KeywordRepository extends JpaRepository<Keyword, Long> {


    @Query("update Keyword k set k.enabled=:enabled where k.keywordId = :keywordId")
    @Modifying
    int updateEnabledByKeywordId(@Param("keywordId") Long keywordId, @Param("enabled") boolean enabled);

    List<Keyword> getKeywordsByCategoryCategoryId(Long categoryId);
}
