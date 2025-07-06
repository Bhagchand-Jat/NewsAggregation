package com.newsaggregator.client.util;

public enum NotificationMenu implements Labeled {
    VIEW(UiText.VIEW_NOTIFICATIONS), CONFIGURE(UiText.CONFIGURE_NOTIFICATIONS), BACK(UiText.BACK_LABEL);
    private final String label;

    NotificationMenu(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
