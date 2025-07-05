package com.news_aggregation_system.service.news.pruning;

import com.news_aggregation_system.model.Article;

import java.util.Locale;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AdminKeywordPruner {
    private static final Set<Function<Article, String>> TEXT_SOURCES = Set.of(
            Article::getTitle,
            Article::getDescription,
            Article::getContent
    );

    public static java.util.List<Article> prune(java.util.List<Article> articles,
                                                Set<String> adminDisabledKeywords) {

        if (adminDisabledKeywords.isEmpty()) return articles;

        return articles.stream()
                .filter(a -> !containsDisabledWord(a, adminDisabledKeywords))
                .toList();
    }

    private static boolean containsDisabledWord(Article article, Set<String> disabledKeywords) {
        String text = TEXT_SOURCES.stream()
                .map(fn -> fn.apply(article).toLowerCase(Locale.ROOT))
                .collect(Collectors.joining(" "));
        return disabledKeywords.stream().anyMatch(text::contains);
    }
}
