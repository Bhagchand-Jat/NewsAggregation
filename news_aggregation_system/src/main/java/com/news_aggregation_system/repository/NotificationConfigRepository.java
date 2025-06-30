package com.news_aggregation_system.repository;

import com.news_aggregation_system.model.NotificationConfig;
import com.news_aggregation_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationConfigRepository extends JpaRepository<NotificationConfig, Long> {
    Optional<NotificationConfig> findByUser(User user);
}
