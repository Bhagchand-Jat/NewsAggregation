package com.news_aggregation_system.controller;

import com.news_aggregation_system.dto.NotificationDTO;
import com.news_aggregation_system.response.ApiResponse;
import com.news_aggregation_system.service.notification.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/{userId}/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<List<NotificationDTO>>> getNotifications(
            @PathVariable("userId") Long userId,
            @RequestParam(defaultValue = "false", value = "isViewed") boolean isViewed) {

        return ResponseEntity.ok(ApiResponse.ok(
                notificationService.getNotificationsByUserIdAndReadStatusAndUpdateMarkAsRead(userId, isViewed)));
    }

}
