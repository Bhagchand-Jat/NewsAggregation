package com.news_aggregation_system.controller;

import com.news_aggregation_system.dto.NotificationDTO;
import com.news_aggregation_system.response.ApiResponse;
import com.news_aggregation_system.security.CustomUserDetails;
import com.news_aggregation_system.service.notification.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/me/notifications")
@PreAuthorize("hasRole('USER')")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<List<NotificationDTO>>> getNotifications(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam(defaultValue = "false", value = "isViewed") boolean isViewed) {

        return ResponseEntity.ok(ApiResponse.ok(
                notificationService.getNotificationsByUserIdAndReadStatusAndUpdateMarkAsRead(user.getUserId(), isViewed)));
    }

}
