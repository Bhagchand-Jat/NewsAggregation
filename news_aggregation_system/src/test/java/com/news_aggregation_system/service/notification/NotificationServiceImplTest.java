package com.news_aggregation_system.service.notification;

import com.news_aggregation_system.dto.ArticleDTO;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock NotificationRepository repo;

    @InjectMocks NotificationServiceImpl service;

    @Test
    @DisplayName("getNotificationsByUserIdAndReadStatusAndUpdateMarkAsRead → returns list")
    void getNotifications_success() {
        Notification n = new Notification(); n.setNotificationId(2L);
        when(repo.findByUserUserIdAndViewed(5L,false)).thenReturn(List.of(n));
        NotificationDTO dto = new NotificationDTO(); dto.setNotificationId(1L);
        when(NotificationMapper.toDto(n)).thenReturn(dto);
        List<NotificationDTO> result = service.getNotificationsByUserIdAndReadStatusAndUpdateMarkAsRead(5L,false);
        assertThat(result).hasSize(1);
        verify(repo).saveAll(List.of(n));
    }



    @Test
    @DisplayName("deleteByIsViewedTrueAndReadAtBefore → repository called")
    void deleteOlderViewed() {
        Date limit = Date.from(Instant.now());
        service.deleteByIsViewedTrueAndReadAtBefore(limit);
        verify(repo).deleteByViewedTrueAndReadAtBefore(limit);
    }
}
