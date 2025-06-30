package com.newsaggregator.client.util;

import static com.newsaggregator.client.util.UiText.VIEW_LIST_EXTERNAL_SOURCES_STATUS;

public enum AdminMainMenu implements Labeled {
    SOURCES_STATUS(VIEW_LIST_EXTERNAL_SOURCES_STATUS),
    SOURCE_DETAILS(UiText.VIEW_LIST_EXTERNAL_SOURCE_DETAILS),
    UPDATE_SOURCE_KEY(UiText.UPDATE_NEWS_SOURCE_API_KEY),
    ADD_CATEGORY(UiText.ADD_CATEGORY),
    UPDATE_CATEGORY_STATUS(UiText.UPDATE_CATEGORY_STATUS),
    VIEW_CATEGORIES(UiText.VIEW_CATEGORIES),
    VIEW_ARTICLES(UiText.VIEW_ARTICLES),
    UPDATE_ARTICLE_STATUS(UiText.UPDATE_ARTICLE_STATUS),
    LOGOUT(UiText.LOGOUT);

    private final String label;

    AdminMainMenu(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
