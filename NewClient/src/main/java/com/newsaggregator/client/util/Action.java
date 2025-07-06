package com.newsaggregator.client.util;

public enum Action implements Labeled {
    SAVE("Save Article"), REPORT("Report Article"), LIKE("Like Article"), DISLIKE("Dislike Article"), DELETE("Delete Saved Article"), BACK(UiText.BACK_LABEL);
    final String label;

    Action(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return this.label;
    }
}
