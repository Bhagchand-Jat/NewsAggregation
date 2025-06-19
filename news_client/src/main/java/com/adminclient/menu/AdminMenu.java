package com.adminclient.menu;



import java.util.List;
import com.adminclient.client.CategoryClient;
import com.adminclient.client.SourceClient;
import com.adminclient.dto.SourceDTO;
import com.adminclient.util.ConsoleUtil;

public class AdminMenu {

    private final SourceClient sourceClient;
    private final CategoryClient categoryClient;
    private final ConsoleUtil console;

    public AdminMenu(SourceClient sc, CategoryClient cc, ConsoleUtil console) {
        this.sourceClient = sc;
        this.categoryClient = cc;
        this.console = console;
    }

    public void show() throws java.io.IOException {
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. View Sources");
            System.out.println("2. View Source Details");
            System.out.println("3. Update Source");
            System.out.println("4. Add Category");
            System.out.println("5. Logout");
            int ch = console.readInt("Choose: ");
            switch (ch) {
                case 1 -> viewSources();
                case 2 -> viewSourceDetails();
                case 3 -> updateSource();
                case 4 -> addCategory();
                case 5 -> { return; }
                default -> System.out.println("Invalid");
            }
        }
    }

    private void viewSources() throws java.io.IOException {
        List<SourceDTO> list = sourceClient.findAll();
        list.forEach(s -> System.out.printf("ID:%-3d URL:%-50s active=%s%n",
                s.sourceId(), s.sourceUrl(), s.active()));
    }

    private void viewSourceDetails() throws java.io.IOException {
        long id = console.readLong("Source ID: ");
        SourceDTO s = sourceClient.findById(id);
        System.out.printf("ID:%d\nURL:%s\nAPI Key:%s\nActive:%s%n",
                s.sourceId(), s.sourceUrl(), s.sourceApiKey(), s.active());
    }

    private void updateSource() {
        long id = console.readLong("Source ID: ");
        String url = console.readLine("New URL: ");
        boolean active = console.readBoolean("Active (y/n): ");
        sourceClient.update(id, url, active);
        System.out.println("Updated.");
    }

    private void addCategory() {
        String name = console.readLine("Category name: ");
        categoryClient.addCategory(name);
        System.out.println("Category added.");
    }
}
