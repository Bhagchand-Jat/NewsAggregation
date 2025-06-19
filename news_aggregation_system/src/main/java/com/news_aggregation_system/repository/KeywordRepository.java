package com.news_aggregation_system.repository;

import com.news_aggregation_system.model.Keyword;
import com.news_aggregation_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    List<Keyword> findAllByUser(User user);

    Optional<Keyword> findByKeywordIdAndUser_UserId(Long keywordId, Long userId);

}
