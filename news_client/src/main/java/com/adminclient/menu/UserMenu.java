package com.adminclient.menu;


import java.util.List;

import com.adminclient.client.ArticleClient;
import com.adminclient.dto.ArticleDTO;
import com.adminclient.util.ConsoleUtil;

public class UserMenu {

    private final ArticleClient articleClient;
    private final NotificationMenu notificationMenu;
    private final ConsoleUtil console;

    public UserMenu(ArticleClient articleClient, NotificationMenu notificationMenu, ConsoleUtil console) {
        this.articleClient = articleClient;
        this.notificationMenu = notificationMenu;
        this.console = console;
    }

    public void show() throws java.io.IOException {
        while (true) {
            System.out.println("\n=== User Menu ===");
            System.out.println("1. Headlines (Today)");
            System.out.println("2. Search");
            System.out.println("3. Saved Articles");
            System.out.println("4. Notifications");
            System.out.println("5. Logout");
            int choice = console.readInt("Choose: ");
            switch (choice) {
                case 1 -> showHeadlines();
                case 2 -> search();
                case 3 -> saved();
                case 4 -> notificationMenu.show();
                case 5 -> { return; }
                default -> System.out.println("Invalid");
            }
        }
    }

    private void showHeadlines() throws java.io.IOException {
        List<ArticleDTO> list = articleClient.headlinesToday();
        list.forEach(this::printArticle);
        if (!list.isEmpty()) {
            if (console.readBoolean("Save one? (y/n): ")) {
                long aid = console.readLong("Article ID: ");
                articleClient.saveArticle(aid);
            }
        }
    }

    private void search() throws java.io.IOException {
        String q = console.readLine("Query: ");
        List<ArticleDTO> list = articleClient.search(q);
        list.forEach(this::printArticle);
    }

    private void saved() throws java.io.IOException {
        List<ArticleDTO> list = articleClient.savedArticles();
        list.forEach(this::printArticle);
        if (!list.isEmpty()) {
            if (console.readBoolean("Delete one? (y/n): ")) {
                long aid = console.readLong("Article ID: ");
                articleClient.deleteSaved(aid);
            }
        }
    }

    private void printArticle(ArticleDTO articleDTO) {
        System.out.printf("ID:%-4d %s (%s)\nURL:%s\n%n",
                articleDTO.getArticleId(), articleDTO.getTitle(), articleDTO.getCategories(), articleDTO.getUrl());
    }
}
