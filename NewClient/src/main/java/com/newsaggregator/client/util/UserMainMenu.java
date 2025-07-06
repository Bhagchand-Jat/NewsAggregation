package com.newsaggregator.client.util;


public enum UserMainMenu implements Labeled {
    HEADLINES(UiText.VIEW_HEADLINES), SAVED_ARTICLES(UiText.VIEW_SAVED_ARTICLES), SEARCH(UiText.SEARCH_ARTICLES), REPORTS(UiText.REPORTED_ARTICLES), NOTIFICATIONS(UiText.NOTIFICATIONS), LOGOUT(UiText.LOGOUT);

    private final String label;

    UserMainMenu(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
