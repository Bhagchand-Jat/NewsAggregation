package com.newsaggregator.client.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Constant {


        // ─── Date Formatters ─────────────────────────────────────
        public static final DateTimeFormatter dateFormatter    = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        public static final DateTimeFormatter isoDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        public static final DateTimeFormatter timeFormatter    = DateTimeFormatter.ofPattern("h:mma");
        public static final SimpleDateFormat  simpleDateFormatter = new SimpleDateFormat("dd-MMM-yyyy");

        // ─── Placeholder Tokens ──────────────────────────────────
        public static final String USER_ID       = "{userId}";
        public static final String ARTICLE_ID    = "{articleId}";
        public static final String CATEGORY_ID   = "{categoryId}";
    public static final String NEWS_SOURCE_ID= "{newsSourceId}";
        public static final String KEYWORD_NAME  = "{keywordName}";

        // ─── Config Parameters ──────────────────────────────────
        public static final String NEW_API_KEY   = "newApiKey";
        public static final String IS_ENABLED    = "isEnabled";
        public static final String NEW_THRESHOLD = "newThreshold";

        // ─── Base URL ────────────────────────────────────────────
        private static final String BASE_URL = "http://localhost:8080/api";

        // ─── Auth Endpoints ──────────────────────────────────────
        public static final String LOGIN_URL     = BASE_URL + "/auth/login";
        public static final String SIGNUP_URL    = BASE_URL + "/auth/signup";

    public static final String API_USERS_SAVED_ARTICLES           = BASE_URL + "/users/{userId}/saved-articles";
        public static final String API_USERS_SAVE_ARTICLE             = BASE_URL + "/users/save-article";
        public static final String API_USERS_DELETE_SAVED_ARTICLE     = BASE_URL + "/users/{userId}/saved-article/{articleId}";
        public static final String API_USERS_REPORT_ARTICLE           = BASE_URL + "/users/report-article";
        public static final String API_USERS_REPORTS                  = BASE_URL + "/users/{userId}/articles-reports";
        public static final String API_USERS_CATEGORIES_ENABLED       = BASE_URL + "/users/categories/enabled";
        public static final String API_USERS_MARK_ARTICLE_AS_READ     = BASE_URL + "/users/{userId}/article/{articleId}/markAsRead";
        public static final String API_USERS_ARTICLES_READ_HISTORY    = BASE_URL + "/users/{userId}/articles-read-history";

        // ─── Notification Preferences ─────────────────────────────
        public static final String API_USERS_NOTIFICATIONS_PREFERENCES_CATEGORIES        = BASE_URL + "/users/{userId}/notifications/preferences/categories/{categoryId}";
        public static final String API_USERS_NOTIFICATIONS_PREFERENCES_CATEGORIES_LIST   = BASE_URL + "/users/{userId}/notifications/preferences/categories";
        public static final String API_USERS_NOTIFICATIONS_PREFERENCES_KEYWORDS          = BASE_URL + "/users/{userId}/notifications/preferences/categories/{categoryId}/keywords";

        // ─── News Endpoints ───────────────────────────────────────
        public static final String API_NEWS        = BASE_URL + "/news";
        public static final String API_NEWS_FILTER = BASE_URL + "/news/{userId}/filter";

        // ─── Reactions Endpoints ──────────────────────────────────
        public static final String API_REACTIONS_LIKE    = BASE_URL + "/reactions/like";
        public static final String API_REACTIONS_DISLIKE = BASE_URL + "/reactions/dislike";

        // ─── Admin - News Sources ─────────────────────────────────
        public static final String API_ADMIN_NEWS_SOURCES         = BASE_URL + "/admin/news-sources";
        public static final String API_ADMIN_NEWS_SOURCES_APIKEY  = BASE_URL + "/admin/news-sources/{newsSourceId}/apikey";

        // ─── Admin - Articles ─────────────────────────────────────
        public static final String API_ADMIN_ARTICLES_STATUS      = BASE_URL + "/admin/articles/{articleId}/status";
        public static final String API_ADMIN_REPORTED_ARTICLES    = BASE_URL + "/admin/reported-articles";
        public static final String API_ADMIN_ARTICLE_REPORTS      = BASE_URL + "/admin/{articleId}/article-reports";

        // ─── Admin - Categories ───────────────────────────────────
        public static final String API_ADMIN_CATEGORIES           = BASE_URL + "/admin/categories";
        public static final String API_ADMIN_CATEGORIES_ID        = BASE_URL + "/admin/categories/{categoryId}";
        public static final String API_ADMIN_CATEGORIES_KEYWORDS  = BASE_URL + "/admin/categories/{categoryId}/keywords";
        public static final String API_ADMIN_CATEGORY_KEYWORDS_NAME = BASE_URL + "/admin/categories/{categoryId}/keywords/{keywordName}";

        // ─── Admin - Config Threshold ─────────────────────────────
        public static final String API_ADMIN_CONFIG_THRESHOLD     = BASE_URL + "/admin/config/threshold";

        // ─── Notifications ────────────────────────────────────────
        public static final String API_USER_NOTIFICATIONS         = BASE_URL + "/user/{userId}/notifications";

        // ─── Utilities ────────────────────────────────────────────
        public static Date toUtilDate(String dateStr) {
            LocalDate localDate = LocalDate.parse(dateStr, isoDateFormatter);
            return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
    }
