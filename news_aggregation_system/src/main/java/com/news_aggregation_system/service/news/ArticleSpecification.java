package com.news_aggregation_system.service.news;

import com.news_aggregation_system.dto.ArticleFilterRequestDTO;
import com.news_aggregation_system.model.Article;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

import static com.news_aggregation_system.service.common.Constant.*;

public class ArticleSpecification {

    public static Specification<Article> adminEnabledOnly() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.isTrue(root.get(ENABLED)),
                criteriaBuilder.isTrue(root.joinSet(CATEGORIES).get(ENABLED))
        );
    }

    public static Specification<Article> applyFilters(ArticleFilterRequestDTO filter) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (hasText(filter.getKeyword())) {
                String keyword = "%" + filter.getKeyword().toLowerCase() + "%";
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.or(
                                criteriaBuilder.like(criteriaBuilder.lower(root.get(TITLE)), keyword),
                                criteriaBuilder.like(criteriaBuilder.lower(root.get(DESCRIPTION)), keyword),
                                criteriaBuilder.like(criteriaBuilder.lower(root.get(CONTENT)), keyword)
                        ));
            } else if (filter.getDate() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get(PUBLISHED_AT).as(LocalDate.class), filter.getDate()));
            } else if (isRangeWithCategory(filter)) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.between(root.get(PUBLISHED_AT), filter.getFrom(), filter.getTo()),
                        criteriaBuilder.equal(root.joinSet(CATEGORIES).get(CATEGORY_ID), filter.getCategoryId()));
            } else if (isRange(filter)) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.between(root.get(PUBLISHED_AT), filter.getFrom(), filter.getTo()));
            } else if (filter.getCategoryId() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.joinSet(CATEGORIES).get(CATEGORY_ID), filter.getCategoryId()));
            }

            return predicate;
        };
    }

    private static boolean isRange(ArticleFilterRequestDTO filter) {
        return filter.getFrom() != null && filter.getTo() != null;
    }

    private static boolean isRangeWithCategory(ArticleFilterRequestDTO filter) {
        return isRange(filter) && filter.getCategoryId() != null;
    }

    private static boolean hasText(String s) {
        return s != null && !s.isBlank();
    }
}

