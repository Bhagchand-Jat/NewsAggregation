package com.newsaggregator.client.util;

import static com.newsaggregator.client.util.UiText.VIEW_LIST_EXTERNAL_SOURCES_STATUS;

public enum AdminMainMenu implements Labeled {
    SOURCES_STATUS(VIEW_LIST_EXTERNAL_SOURCES_STATUS),
    SOURCE_DETAILS(UiText.VIEW_LIST_EXTERNAL_SOURCE_DETAILS),
    UPDATE_SOURCE_KEY(UiText.UPDATE_NEWS_SOURCE_API_KEY),
    ADD_CATEGORY(UiText.ADD_CATEGORY),
    VIEW_CATEGORIES(UiText.VIEW_CATEGORIES),
    VIEW_ARTICLES(UiText.VIEW_ARTICLES),
    UPDATE_ARTICLE_STATUS(UiText.UPDATE_ARTICLE_STATUS),
    VIEW_REPORTED_ARTICLES(UiText.VIEW_REPORTED_ARTICLES),
    VIEW_ARTICLE_REPORT_THRESHOLD(UiText.VIEW_ARTICLE_REPORT_THRESHOLD),
    UPDATE_ARTICLE_REPORT_THRESHOLD(UiText.UPDATE_ARTICLE_REPORT_THRESHOLD),
    LOGOUT(UiText.LOGOUT);

    private final String label;

    AdminMainMenu(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
