package com.newsaggregator.client.model;

public record Article(
        String id,
        String title,
        String summary,
        String source,
        String url,
        Category category) {

    @Override
    public String toString() {
        return String.format(
                "Article Id: %s %n %s %n source: %s %n url: %s %n category: %s %n",
                id, title, source, url, category);
    }
}
