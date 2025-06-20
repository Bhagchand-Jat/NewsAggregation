package com.news_aggregation_system.repository;

import com.news_aggregation_system.model.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemConfigRepository extends JpaRepository<SystemConfig, Long> {
    SystemConfig findTopByOrderByIdDesc();
}
