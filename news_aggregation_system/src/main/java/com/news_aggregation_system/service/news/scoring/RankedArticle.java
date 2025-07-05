package com.news_aggregation_system.service.news.scoring;

import com.news_aggregation_system.model.Article;

import java.util.Comparator;

public record RankedArticle(Article article, int score) {
    public static Comparator<RankedArticle> comparator() {
        return Comparator.comparingInt(RankedArticle::score).reversed()
                .thenComparing(rankedArticle -> rankedArticle.article().getPublishedAt(), Comparator.reverseOrder());
    }
}
