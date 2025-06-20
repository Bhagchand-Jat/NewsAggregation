package com.adminclient.menu;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.adminclient.client.NotificationClient;
import com.adminclient.dto.NotificationDTO;
import com.adminclient.util.ConsoleUtil;

public class NotificationMenu {

    private final NotificationClient notificationClient;
    private final ConsoleUtil console;

    public NotificationMenu(NotificationClient notificationClient, ConsoleUtil console) {
        this.notificationClient = notificationClient;
        this.console = console;
    }

    public void show() throws java.io.IOException {
        while (true) {
            System.out.println("\n=== Notifications ===");
            System.out.println("1. View Notifications");
            System.out.println("2. Configure Notifications");
            System.out.println("3. Back");
            int ch = console.readInt("Choose: ");
            switch (ch) {
                case 1 -> viewNotifications();
                case 2 -> configure();
                case 3 -> { return; }
                default -> System.out.println("Invalid");
            }
        }
    }

    private void viewNotifications() throws java.io.IOException {
        List<NotificationDTO> list = notificationClient.findAllUnRead();
        list.forEach(notification -> System.out.printf("ID:%-3d %s [%s]\n",
                notification.getNotificationId(), notification.getMessage(), notification.getCreatedAt()));
    }

    private void configure() {
        String category = console.readLine("Enter category to toggle (or 'keywords'): ");
        boolean enabled = console.readBoolean("Enable? (y/n): ");
        Map<String, Object> conf = new HashMap<>();
        conf.put(category, enabled);
        notificationClient.configure(conf);
        System.out.println("Configuration updated.");
    }
}
