package com.news_aggregation_system.repository;

import com.news_aggregation_system.model.NewsSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface NewsSourceRepository extends JpaRepository<NewsSource, Long> {
  List<NewsSource> findByEnabledTrue();

  @Modifying
  @Transactional
  @Query("UPDATE NewsSource n SET n.lastAccessed = :timestamp WHERE n.id = :id")
  void updateLastAccessed(@Param("id") Long id, @Param("timestamp") LocalDateTime timestamp);
}
