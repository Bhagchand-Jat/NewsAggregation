package com.adminclient.menu;

import java.text.SimpleDateFormat;
import java.util.List;
import com.adminclient.client.CategoryClient;
import com.adminclient.client.SourceClient;
import com.adminclient.dto.CategoryDTO;
import com.adminclient.dto.NewsSourceDTO;
import com.adminclient.dto.SourceDTO;
import com.adminclient.util.ConsoleUtil;

public class AdminMenu {

    private final SourceClient sourceClient;
    private final CategoryClient categoryClient;
    private final ConsoleUtil console;

    public AdminMenu(SourceClient sourceClient, CategoryClient categoryClient, ConsoleUtil console) {
        this.sourceClient = sourceClient;
        this.categoryClient = categoryClient;
        this.console = console;
    }

    public void show() throws java.io.IOException {
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. View the list of external servers and status");
            System.out.println("2. View the external server’s details");
            System.out.println("3. Update/Edit the external server’s details");
            System.out.println("4. Add new News Category");
            System.out.println("5. View News Categories");
            System.out.println("6. Logout");
            int ch = console.readInt("Choose: ");
            switch (ch) {
                case 1 -> viewSources();
                case 2 -> viewSourceDetails();
                case 3 -> updateSource();
                case 4 -> addCategory();
                case 5 -> viewCategories();
                case 6 -> {
                    return;
                }
                default -> System.out.println("Invalid Choice");
            }
        }
    }

    private void viewSources() throws java.io.IOException {
        List<SourceDTO> sources = sourceClient.findAll();
        System.out.println("List of external servers:");

        for (SourceDTO source : sources) {
            String status = source.isEnabled() ? "Active" : "Not Active";
            String lastAccessed = source.getLastAccessed() != null
                    ? new SimpleDateFormat("dd MMM yyyy").format(source.getLastAccessed())
                    : "Never";

            System.out.printf("%d. %s - %s - last accessed: %s%n",
                    source.getSourceId(),
                    source.getSourceUrl(),
                    status,
                    lastAccessed);
        }
    }

    private void viewSourceDetails() throws java.io.IOException {
        List<SourceDTO> sources = sourceClient.findAll();
        System.out.println("List of external server details:");
        for (SourceDTO source : sources) {
            System.out.printf("%d. %s - %s%n",
                    source.getSourceId(),
                    source.getSourceUrl(),
                    source.getSourceApiKey());
        }
    }

    private void updateSource() {
        System.out.println("Update/Edit the external server’s details");
        long id = console.readLong("Enter the external server ID:");
        String apiKey = console.readLine("Enter the updated API key");
        boolean active = console.readBoolean("Enter updated Status (y/n): ");
        sourceClient.update(new NewsSourceDTO(id, apiKey, active));
    }

    private void addCategory() {
        String name = console.readLine("Category name: ");
        categoryClient.addCategory(name);
    }

    private void viewCategories() {
        List<CategoryDTO> categories = categoryClient.findAll();
        for (CategoryDTO categoryDTO : categories) {
            System.out.printf("ID:%d\nName:%s\nActive:%s%n",
                    categoryDTO.getCategoryId(), categoryDTO.getName(), categoryDTO.isEnabled());
        }
    }
}
