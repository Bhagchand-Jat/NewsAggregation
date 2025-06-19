package com.adminclient;

import org.springframework.web.client.RestTemplate;

import com.adminclient.client.ArticleClient;
import com.adminclient.client.AuthClient;
import com.adminclient.client.CategoryClient;
import com.adminclient.client.NotificationClient;
import com.adminclient.client.SourceClient;
import com.adminclient.menu.AdminMenu;
import com.adminclient.menu.MainMenu;
import com.adminclient.menu.NotificationMenu;
import com.adminclient.menu.UserMenu;
import com.adminclient.session.UserSession;
import com.adminclient.util.ConsoleUtil;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        ConsoleUtil console = new ConsoleUtil(scanner);
        RestTemplate rest = new RestTemplate();
        UserSession session = new UserSession();

        AuthClient authClient = new AuthClient(rest, session);
        SourceClient sourceClient = new SourceClient(rest, session);
        CategoryClient categoryClient = new CategoryClient(rest, session);
        ArticleClient articleClient = new ArticleClient(rest, session);
        NotificationClient notificationClient = new NotificationClient(rest, session);


        AdminMenu adminMenu = new AdminMenu(sourceClient, categoryClient, console);
        NotificationMenu notificationMenu = new NotificationMenu(notificationClient, console);
        UserMenu userMenu = new UserMenu(articleClient, notificationMenu, console);
        MainMenu mainMenu = new MainMenu(authClient, adminMenu, userMenu, console, session);


        mainMenu.loop();
        System.out.println("Goodbye!");
    }
}
