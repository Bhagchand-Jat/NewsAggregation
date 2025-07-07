package com.news_aggregation_system.service.notification;

import com.news_aggregation_system.dto.NotificationDTO;
import com.news_aggregation_system.mapper.NotificationMapper;
import com.news_aggregation_system.model.Notification;
import com.news_aggregation_system.repository.NotificationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock NotificationRepository notificationRepository;

    @InjectMocks NotificationServiceImpl service;

    @Test
    @DisplayName("getNotificationsByUserIdAndReadStatusAndUpdateMarkAsRead → returns list")
    void getNotifications_success() {
        Notification notification = new Notification(); notification.setNotificationId(2L);
        when(notificationRepository.findByUserUserIdAndViewed(5L,false)).thenReturn(List.of(notification));

        List<NotificationDTO> result = service.getNotificationsByUserIdAndReadStatusAndUpdateMarkAsRead(5L,false);
        assertThat(result).hasSize(1);
        verify(notificationRepository).saveAll(List.of(notification));
    }



    @Test
    @DisplayName("deleteByIsViewedTrueAndReadAtBefore → repository called")
    void deleteOlderViewed() {
        Date limit = Date.from(Instant.now());
        service.deleteByIsViewedTrueAndReadAtBefore(limit);
        verify(notificationRepository).deleteByViewedTrueAndReadAtBefore(limit);
    }
}
