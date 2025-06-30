package com.news_aggregation_system.service.notification;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}

