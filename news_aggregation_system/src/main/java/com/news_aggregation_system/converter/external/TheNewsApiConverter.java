package com.news_aggregation_system.converter.external;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.news_aggregation_system.dto.ArticleDTO;
import com.news_aggregation_system.model.Category;
import com.news_aggregation_system.service.admin.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TheNewsApiConverter implements ExternalArticleConverter {
    private static final String IDENTIFIER = "thenewsapi.com";
    private final Logger logger = LoggerFactory.getLogger(TheNewsApiConverter.class);
    private final ObjectMapper MAPPER = new ObjectMapper();


    private final CategoryService categoryService;

    public TheNewsApiConverter(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public boolean supports(String url) {
        return url.contains(IDENTIFIER);
    }

    @Override
    public String buildUrl(String baseUrl, String apiKey) {


        StringBuilder urlBuilder = new StringBuilder(baseUrl);

        if (baseUrl.contains("?")) {
            urlBuilder.append("&");
        } else {
            urlBuilder.append("?");
        }

        urlBuilder
                .append("api_token=").append(apiKey);

        return urlBuilder.toString();
    }

    @Override
    public List<ArticleDTO> convert(String json) throws Exception {
        logger.info(IDENTIFIER);
        var root = MAPPER.readTree(json);
        List<TheNewsApiArticle> articles = MAPPER.convertValue(
                root.get("data"),
                new TypeReference<>() {
                }
        );

        return articles.stream().map(article -> {
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setTitle(article.title);
            articleDTO.setDescription(article.description);
            articleDTO.setContent(article.snippet != null ? article.snippet : article.description);
            articleDTO.setUrl(article.url);
            articleDTO.setSource(article.source);
            articleDTO.setPublishedAt(Date.from(OffsetDateTime.parse(article.published_at).toInstant()));

            if (article.categories == null || article.categories.isEmpty()) {
                CategoryInferenceEngine categoryInferenceEngine = new CategoryInferenceEngine();
                Set<String> inferredCategories = categoryInferenceEngine.inferCategories(
                        articleDTO.getTitle(),
                        articleDTO.getDescription(),
                        articleDTO.getContent());

                Set<Category> categories = categoryService.getOrCreateCategories(inferredCategories);
                articleDTO.setCategories(categories);
            } else {
                Set<Category> categories = categoryService.getOrCreateCategories(new HashSet<>(article.categories));
                articleDTO.setCategories(categories);
            }
            return articleDTO;
        }).collect(Collectors.toList());
    }
}
