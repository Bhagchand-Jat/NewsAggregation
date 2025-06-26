package com.news_aggregation_system.mapper;

import com.news_aggregation_system.dto.NotificationDTO;
import com.news_aggregation_system.model.Notification;
import com.news_aggregation_system.model.User;

public class NotificationMapper {

    public static NotificationDTO toDto(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setNotificationId(notification.getNotificationId());
        dto.setMessage(notification.getMessage());
        dto.setViewed(notification.isViewed());
        dto.setCreatedAt(notification.getCreatedAt());
        dto.setReadAt(notification.getReadAt());
        if (notification.getUser() != null) {
            dto.setUserId(notification.getUser().getUserId());
        }
        return dto;
    }

    public static Notification toEntity(NotificationDTO dto) {
        Notification notification = new Notification();
        notification.setNotificationId(dto.getNotificationId());
        notification.setMessage(dto.getMessage());
        notification.setViewed(dto.isViewed());
        notification.setCreatedAt(dto.getCreatedAt());
        notification.setReadAt(dto.getReadAt());
        if (dto.getUserId() != null) {
            User user = new User();
            user.setUserId(dto.getUserId());
            notification.setUser(user);
        }

        return notification;
    }
}
