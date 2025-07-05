package com.newsaggregator.client.util;

public enum CategoryMenu implements Labeled {

    UPDATE_CATEGORY_STATUS(UiText.UPDATE_CATEGORY_STATUS),
    ADD_KEYWORDS(UiText.ADD_KEYWORDS_TO_CATEGORY),
    VIEW_KEYWORDS(UiText.VIEW_KEYWORDS),
    DELETE_KEYWORD(UiText.DELETE_KEYWORD_FROM_CATEGORY),
    UPDATE_KEYWORD_STATUS(UiText.UPDATE_KEYWORD_STATUS),
    BACK(UiText.BACK_LABEL);

    private final String label;

    CategoryMenu(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
