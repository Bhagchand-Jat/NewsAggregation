package com.newsaggregator.client.util;

import static com.newsaggregator.client.util.UiText.BACK_LABEL;

public enum HeadlineMenu implements Labeled {
    TODAY(UiText.TODAY), DATE_RANGE(UiText.DATE_RANGE), BACK(BACK_LABEL);
    private final String label;

    HeadlineMenu(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return this.label;
    }
}
