package com.news_aggregation_system.service.news.scoring;

import java.util.Set;

public record UserSignals(Set<Long> likedArticleIds,
                          Set<Long> savedArticleIds,
                          Set<Long> readCategoryIds) {
}
