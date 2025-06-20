package com.news_aggregation_system.controller;

import com.news_aggregation_system.dto.NotificationDTO;
import com.news_aggregation_system.response.ApiResponse;
import com.news_aggregation_system.service.notification.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<NotificationDTO>>> list(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "false") boolean read) {

        return ResponseEntity.ok(ApiResponse.ok(
                "Fetched notifications",
                notificationService.getNotificationsByUserIdAndReadStatusAndUpdateMarkAsRead(userId, read)));
    }
}
