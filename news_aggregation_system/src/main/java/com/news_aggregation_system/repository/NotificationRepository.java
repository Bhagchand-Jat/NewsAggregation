package com.news_aggregation_system.repository;

import com.news_aggregation_system.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;


public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserUserIdAndViewed(Long userId, boolean viewed);

    int deleteByViewedTrueAndReadAtBefore(Date date);
}
