package com.news_aggregation_system.repository;

import com.news_aggregation_system.model.Keyword;
import com.news_aggregation_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    List<Keyword> findAllByUser(User user);

    Optional<Keyword> findByKeywordIdAndUser_UserId(Long keywordId, Long userId);

    int deleteKeywordByKeywordIdAndUserUserId(Long keywordId, Long userUserId);

    @Query("update Keyword k set k.enabled=:enabled where k.keywordId = :keywordId")
    @Modifying
    int updateEnabledByKeywordId(@Param("keywordId") Long keywordId, @Param("enabled") boolean enabled);
}
