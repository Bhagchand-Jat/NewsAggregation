package com.newsaggregator.client.controller;

import com.newsaggregator.client.service.AuthService;
import com.newsaggregator.client.session.UserSession;
import com.newsaggregator.client.util.ConsoleUtils;

import java.util.Optional;

import static com.newsaggregator.client.util.UiText.*;

public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public Optional<UserSession> start() {
        while (true) {
            System.out.println("\n" + WELCOME_HEADER);
            System.out.println("1. " + LOGIN_OPTION);
            System.out.println("2. " + SIGNUP_OPTION);
            System.out.println("3. " + EXIT_OPTION);

            String input = ConsoleUtils.readLine(CHOICE);
            switch (input) {
                case "1" -> {
                    return login();
                }
                case "2" -> {
                    return signup();
                }
                case "3" -> {
                    System.exit(0);
                    return Optional.empty();
                }
                default -> System.out.println(INVALID);
            }
        }
    }

    private Optional<UserSession> login() {
        String email = ConsoleUtils.readLine(EMAIL_PROMPT);
        String password = ConsoleUtils.readPassword(PASSWORD_PROMPT);

        return authService.login(email, password);
    }

    private Optional<UserSession> signup() {
        String name = ConsoleUtils.readLine(NAME_PROMPT);
        String email = ConsoleUtils.readLine(EMAIL_PROMPT);
        String password = ConsoleUtils.readLine(PASSWORD_PROMPT);

        return authService.signup(name, email, password);
    }
}
