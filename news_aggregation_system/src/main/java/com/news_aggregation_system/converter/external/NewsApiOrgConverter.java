package com.news_aggregation_system.converter.external;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.news_aggregation_system.dto.ArticleDTO;
import com.news_aggregation_system.dto.CategoryDTO;
import com.news_aggregation_system.model.Category;
import com.news_aggregation_system.service.admin.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class NewsApiOrgConverter implements ExternalArticleConverter {
    private final Logger logger = LoggerFactory.getLogger(NewsApiOrgConverter.class);
    private static final String IDENTIFIER = "newsapi.org";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final CategoryService categoryService;

    public NewsApiOrgConverter(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public boolean supports(String url) {
        return url.contains(IDENTIFIER);
    }

    @Override
    public List<ArticleDTO> convert(String json) throws Exception {

        var root = MAPPER.readTree(json);
        logger.info(IDENTIFIER + "{}", root);
        List<NewsApiOrgArticle> articles = MAPPER.convertValue(
                root.get("articles"),
                new TypeReference<>() {
                });
        logger.info(IDENTIFIER + "{}", root);


        return articles.stream().map(article -> {
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setTitle(article.title);
            articleDTO.setDescription(article.description);
            articleDTO.setContent(article.content);
            articleDTO.setUrl(article.url);
            articleDTO.setSource(article.source != null ? article.source.name : "Unknown");
            articleDTO.setPublishedAt(Date.from(OffsetDateTime.parse(article.publishedAt).toInstant()));
            CategoryInferenceEngine categoryInferenceEngine = new CategoryInferenceEngine();
            Set<String> inferredCategories = categoryInferenceEngine.inferCategories(
                    articleDTO.getTitle(),
                    articleDTO.getDescription(),
                    articleDTO.getContent());

            Set<Category> categories = categoryService.getOrCreateCategories(inferredCategories);
            articleDTO.setCategories(categories);

            return articleDTO;
        }).collect(Collectors.toList());
    }
}