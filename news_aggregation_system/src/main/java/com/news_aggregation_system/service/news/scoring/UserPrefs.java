package com.news_aggregation_system.service.news.scoring;

import java.util.Map;
import java.util.Set;

public record UserPrefs(Set<Long> allowedCategoryIds,
                        Map<Long, Set<String>> allowedKeywordsByCategory) {
}
