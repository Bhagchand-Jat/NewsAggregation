package com.newsaggregator.client;

import com.newsaggregator.client.config.JwtErrorHandler;
import com.newsaggregator.client.config.JwtInterceptor;
import com.newsaggregator.client.controller.AdminController;
import com.newsaggregator.client.controller.AuthController;
import com.newsaggregator.client.controller.UserController;
import com.newsaggregator.client.service.*;
import com.newsaggregator.client.service.impl.*;
import com.newsaggregator.client.session.TokenHolder;
import com.newsaggregator.client.util.HttpUtil;
import org.springframework.web.client.RestTemplate;

public class NewsAggregatorClient {


    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        AuthService authService = new AuthServiceImpl(userService);
        AdminService adminService = new AdminServiceImpl();
        NewsService newsService = new NewsServiceImpl();
        NotificationService notificationService = new NotificationServiceImpl();

        RestTemplate restTemplate = HttpUtil.restTemplate();
        restTemplate.getInterceptors().add(new JwtInterceptor());
        restTemplate.setErrorHandler(new JwtErrorHandler(authService));



        boolean loggedIn = false;
        while (!loggedIn) {
            new AuthController(authService).start();

            if (TokenHolder.isUserLoggedIn()) {
                if (TokenHolder.isAdmin()) {
                    AdminController adminController = new AdminController(adminService, newsService);
                    loggedIn = adminController.start();

                } else {
                    UserController userController = new UserController(newsService, notificationService);
                    loggedIn = userController.start();
                }

            }
        }


    }

}