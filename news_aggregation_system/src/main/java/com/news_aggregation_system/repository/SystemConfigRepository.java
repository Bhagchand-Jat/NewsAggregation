package com.news_aggregation_system.repository;

import com.news_aggregation_system.model.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface SystemConfigRepository extends JpaRepository<SystemConfig, Long> {
    Optional<SystemConfig> findTopByOrderByIdDesc();

    @Transactional
    @Modifying
    @Query("update SystemConfig s set s.reportThreshold = :reportThreshold where s.id = :systemConfigId")
    int updateReportThresholdById(@Param("reportThreshold") int reportThreshold, @Param("systemConfigId") Long systemConfigId);
}
