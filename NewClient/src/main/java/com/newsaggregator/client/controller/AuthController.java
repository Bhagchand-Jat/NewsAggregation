package com.newsaggregator.client.controller;

import com.newsaggregator.client.service.AuthService;
import com.newsaggregator.client.util.ConsoleUtils;

import static com.newsaggregator.client.util.UiText.*;

public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public void start() {
        while (true) {
            System.out.println("\n" + WELCOME_HEADER);
            System.out.println("1. " + LOGIN_OPTION);
            System.out.println("2. " + SIGNUP_OPTION);
            System.out.println("3. " + EXIT_OPTION);

            String input = ConsoleUtils.readLine(CHOICE);
            switch (input) {
                case "1" -> {
                    login();
                    return;
                }
                case "2" -> {
                    signup();
                    return;
                }
                case "3" -> {
                    System.exit(0);
                    return;
                }
                default -> System.out.println(INVALID);
            }
        }
    }

    private void login() {
        String email = ConsoleUtils.readLine(EMAIL_PROMPT);
        String password = ConsoleUtils.readPassword(PASSWORD_PROMPT);

        authService.login(email, password);
    }

    private void signup() {
        String name = ConsoleUtils.readLine(NAME_PROMPT);
        String email = ConsoleUtils.readLine(EMAIL_PROMPT);
        String password = ConsoleUtils.readLine(PASSWORD_PROMPT);

        authService.signup(name, email, password);
    }
}
