package com.newsaggregator.client;

import com.newsaggregator.client.controller.AdminController;
import com.newsaggregator.client.controller.AuthController;
import com.newsaggregator.client.controller.UserController;
import com.newsaggregator.client.service.AdminService;
import com.newsaggregator.client.service.AuthService;
import com.newsaggregator.client.service.NewsService;
import com.newsaggregator.client.service.NotificationService;
import com.newsaggregator.client.service.impl.AdminServiceImpl;
import com.newsaggregator.client.service.impl.AuthServiceImpl;
import com.newsaggregator.client.service.impl.NewsServiceImpl;
import com.newsaggregator.client.service.impl.NotificationServiceImpl;
import com.newsaggregator.client.session.UserSession;

import java.util.Optional;

public class NewsAggregatorClient {


    public static void main(String[] args) {
        AuthService authService = new AuthServiceImpl();
        AdminService adminService = new AdminServiceImpl();
        NewsService newsService = new NewsServiceImpl();
        NotificationService notificationService = new NotificationServiceImpl();


        boolean loggedIn = false;
        while (!loggedIn) {
            Optional<UserSession> session = new AuthController(authService).start();

            if (session.isPresent()) {
                if (session.get().isAdmin()) {
                    AdminController adminController = new AdminController(adminService, newsService, session.get());
                    loggedIn = adminController.start();

                } else {
                    UserController userController = new UserController(newsService, notificationService, session.get());
                    loggedIn = userController.start();
                }

            }
        }


    }

}