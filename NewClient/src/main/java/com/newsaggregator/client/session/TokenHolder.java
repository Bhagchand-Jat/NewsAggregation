package com.newsaggregator.client.session;

public class TokenHolder {
    private static volatile String accessToken;
    private static volatile String refreshToken;
    private static volatile String name;
    private static volatile String role;
    private static volatile boolean loggedIn;

    private TokenHolder() {
    }

    public static synchronized void setTokens(String access, String refresh) {
        TokenHolder.accessToken = access;
        TokenHolder.refreshToken = refresh;
        TokenHolder.loggedIn = true;
    }


    public static synchronized String getAccessToken() {
        return accessToken;
    }

    public static synchronized String getRefreshToken() {
        return refreshToken;
    }

    public static synchronized boolean hasRefresh() {
        return refreshToken != null && !refreshToken.isBlank();
    }

    public static synchronized void clear() {
        accessToken = null;
        refreshToken = null;
        name = null;
        role = null;
        loggedIn = false;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        TokenHolder.name = name;
    }

    public static void setRole(String role) {
        TokenHolder.role = role;
    }

    public static boolean isAdmin() {
        return role.equals("ADMIN");
    }

    public static boolean isUserLoggedIn() {
        return loggedIn;
    }
}
