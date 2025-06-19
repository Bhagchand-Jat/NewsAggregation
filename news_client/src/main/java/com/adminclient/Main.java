package com.adminclient;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.adminclient.auth.AuthServiceClient;
import com.adminclient.client.CategoryClient;
import com.adminclient.client.SourceClient;
import com.adminclient.dto.CategoryDTO;
import com.adminclient.dto.SourceDTO;
import com.adminclient.session.UserSession;

public class Main {
    private static final RestTemplate REST = new RestTemplate();
    private static final SourceClient SOURCE = new SourceClient(REST);
    private static final CategoryClient CATEGORY = new CategoryClient(REST);

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        UserSession session = new UserSession();
        AuthServiceClient authClient = new AuthServiceClient(session);

        while (true) {
            header("News Aggregation Client");
            System.out.print("1. Login\n2. Exit\nChoice: ");
            int input = scanner.nextInt();
            scanner.nextLine();
            if (input == 1) {
                System.out.print("Email: ");
                String emailInput = scanner.nextLine();
                System.out.print("Password: ");
                String passwordInput = scanner.nextLine();
                if (authClient.login(emailInput, passwordInput) && session.isAdmin()) {
                    adminMenu(scanner);
                } else {

                }

            } else if (input == 2)
                break;
        }
        System.out.println("Bye!");
    }

    private static void adminMenu(Scanner sc) throws IOException {
        while (true) {
            header("Admin Menu");
            System.out.print("""
                    1. View Sources
                    2. Update Source
                    3. View Source Details
                    4. Show Categories
                    5. Add Category
                    6. Back
                    Choose: """);
            switch (sc.nextInt()) {
                case 1 -> viewSources();
                case 2 -> updateSource(sc);
                case 3 -> viewSourceDetails(sc);
                case 4 -> viewCategories();
                case 5 -> addCategory(sc);
                case 6 -> {
                    return;
                }
                default -> System.out.println("Invalid");
            }
            sc.nextLine(); // consume newline
        }
    }

    /* -------------------- SOURCES -------------------- */

    private static void viewSources() throws IOException {
        header("📡 Sources");
        List<SourceDTO> list = SOURCE.findAll();
        list.forEach(s -> System.out.printf(
                "ID:%-4d %-45s | active=%-5s%n",
                s.sourceId(), s.sourceUrl(), s.active()));
    }

    private static void viewSourceDetails(Scanner sc) throws IOException {
        System.out.print("Source ID: ");
        long id = sc.nextLong();
        SourceDTO s = SOURCE.findById(id);
        header("🔍 Source Details");
        System.out.printf("""
                ID       : %d
                URL      : %s
                API key  : %s
                Active   : %s%n""",
                s.sourceId(), s.sourceUrl(), s.sourceApiKey(), s.active());
    }

    private static void updateSource(Scanner scanner) {
        System.out.print("Source ID: ");
        long id = scanner.nextLong();
        scanner.nextLine();
        System.out.print("New URL: ");
        String url = scanner.nextLine();
        System.out.print("Active (true/false): ");
        boolean active = Boolean.parseBoolean(scanner.nextLine());
        SOURCE.update(id, null, url, active);
        System.out.println("Source updated.");
    }


    private static void viewCategories() throws IOException {
        header("📂 Categories");
        List<CategoryDTO> list = CATEGORY.findAll();
        list.forEach(c -> System.out.printf("ID:%-4d %s%n", c.categoryId(), c.name()));
    }

    private static void addCategory(Scanner sc) {
        System.out.print("Category name: ");
        String name = sc.nextLine();
        CATEGORY.add(name);
        System.out.println("➕ Category added.");
    }

    /* -------------------- HELPERS -------------------- */
    private static void header(String title) {
        System.out.println("\n=== " + title + " ===");
    }
}
