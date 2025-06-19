package com.adminclient.menu;

import com.adminclient.client.AuthClient;
import com.adminclient.session.UserSession;
import com.adminclient.util.ConsoleUtil;

public class MainMenu {

    private final AuthClient authClient;
    private final AdminMenu adminMenu;
    private final UserMenu userMenu;
    private final ConsoleUtil console;
    private final UserSession session;

    public MainMenu(AuthClient auth, AdminMenu adminMenu, UserMenu userMenu,
                    ConsoleUtil console, UserSession session) {
        this.authClient = auth;
        this.adminMenu = adminMenu;
        this.userMenu = userMenu;
        this.console = console;
        this.session = session;
    }

    public void loop() throws java.io.IOException {
        while (true) {
            System.out.println("\n=== News Aggregator ===");
            System.out.println("1. Login");
            System.out.println("2. Sign up");
            System.out.println("3. Exit");
            int ch = console.readInt("Choose: ");
            switch (ch) {
                case 1 -> login();
                case 2 -> signup();
                case 3 -> { return; }
                default -> System.out.println("Invalid");
            }
        }
    }

    private void login() throws java.io.IOException {
        String email = console.readLine("Email: ");
        String pwd   = console.readLine("Password: ");
        if (authClient.login(email, pwd)) {
            if (session.isAdmin()) adminMenu.show();
            else userMenu.show();
            session.logout();
        } else {
            System.out.println("Login failed.");
        }
    }

    private void signup() {
        String name  = console.readLine("Name: ");
        String email = console.readLine("Email: ");
        String pwd   = console.readLine("Password: ");
        authClient.signup(name, email, pwd);
    }
}
