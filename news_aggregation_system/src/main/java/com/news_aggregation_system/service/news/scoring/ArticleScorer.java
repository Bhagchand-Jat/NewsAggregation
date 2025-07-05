package com.news_aggregation_system.service.news.scoring;

import com.news_aggregation_system.model.Article;
import com.news_aggregation_system.model.Category;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ArticleScorer {
    private static final int SCORE_CATEGORY_MATCH = 2;
    private static final int SCORE_KEYWORD_MATCH = 1;
    private static final int SCORE_LIKED = 4;
    private static final int SCORE_SAVED = 3;
    private static final int SCORE_HISTORY = 1;

    private static final List<Function<Article, String>> TEXT = List.of(
            Article::getTitle,
            Article::getDescription,
            Article::getContent
    );

    public static int score(Article article,
                            UserPrefs userPreference,
                            UserSignals userSignals) {

        int score = 0;

        boolean categoryMatched = article.getCategories().stream()
                .map(Category::getCategoryId)
                .anyMatch(userPreference.allowedCategoryIds()::contains);
        if (categoryMatched) score += SCORE_CATEGORY_MATCH;

        Set<String> userKeywords = article.getCategories().stream()
                .map(category -> userPreference.allowedKeywordsByCategory().getOrDefault(category.getCategoryId(), Set.of()))
                .flatMap(Set::stream)
                .collect(Collectors.toSet());

        String text = TEXT.stream()
                .map(fn -> fn.apply(article).toLowerCase(Locale.ROOT))
                .collect(Collectors.joining(" "));

        if (!userKeywords.isEmpty() && userKeywords.stream().anyMatch(text::contains)) score += SCORE_KEYWORD_MATCH;

        if (userSignals.likedArticleIds().contains(article.getArticleId())) score += SCORE_LIKED;
        else if (userSignals.savedArticleIds().contains(article.getArticleId())) score += SCORE_SAVED;

        if (article.getCategories().stream()
                .map(Category::getCategoryId)
                .anyMatch(userSignals.readCategoryIds()::contains)) score += SCORE_HISTORY;

        return score;
    }
}
