package com.news_aggregation_system.repository;

import com.news_aggregation_system.model.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface SystemConfigRepository extends JpaRepository<SystemConfig, Long> {
    Optional<SystemConfig> findTopByOrderByIdDesc();

    @Transactional
    @Modifying
    @Query("update SystemConfig s set s.reportThreshold = ?1 where s.id = ?2")
    int updateReportThresholdById(@NonNull int reportThreshold, Long id);
}
