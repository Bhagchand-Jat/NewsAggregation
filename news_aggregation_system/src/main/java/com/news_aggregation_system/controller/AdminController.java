package com.news_aggregation_system.controller;

import com.news_aggregation_system.dto.*;
import com.news_aggregation_system.response.ApiResponse;
import com.news_aggregation_system.service.admin.CategoryService;
import com.news_aggregation_system.service.admin.KeywordService;
import com.news_aggregation_system.service.admin.NewsSourceService;
import com.news_aggregation_system.service.news.NewsAggregationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final NewsSourceService newsSourceService;
    private final CategoryService categoryService;
    private final KeywordService keywordService;
    private final NewsAggregationService newsAggregationService;

    public AdminController(NewsSourceService newsSourceService, CategoryService categoryService, KeywordService keywordService, NewsAggregationService newsAggregationService) {
        this.newsSourceService = newsSourceService;
        this.categoryService = categoryService;
        this.keywordService = keywordService;
        this.newsAggregationService = newsAggregationService;
    }

    @PostMapping("/categories")
    public ResponseEntity<ApiResponse<CategoryDTO>> createCategory(@Valid @RequestBody CategoryDTO dto) {
        return ResponseEntity.ok(ApiResponse.ok("Category created Successfully", categoryService.create(dto)));
    }

    @GetMapping("/categories/{categoryId}/keywords")
    public ResponseEntity<ApiResponse<List<KeywordDTO>>> getKeywordsForCategory(@PathVariable Long categoryId) {

        return ResponseEntity.ok(ApiResponse.ok(keywordService.getAllKeywordsByCategory(categoryId)));
    }

    @PostMapping("/categories/{categoryId}/keywords")
    public ResponseEntity<ApiResponse<Void>> addKeywordsToCategory(@PathVariable Long categoryId, @Valid @RequestBody KeywordListRequest keywordListRequest) {
        keywordService.addKeywordsToCategory(categoryId, keywordListRequest.getKeywords());
        return ResponseEntity.ok(ApiResponse.ok("Keywords created Successfully"));
    }

    @PostMapping("/keywords")
    public ResponseEntity<ApiResponse<KeywordDTO>> createKeyword(@Valid @RequestBody KeywordDTO keywordDTO) {
        return ResponseEntity.ok(ApiResponse.ok("Keyword created Successfully", keywordService.creteKeyword(keywordDTO)));
    }

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getCategories() {
        return ResponseEntity.ok(ApiResponse.ok(categoryService.getAll()));
    }

    @PatchMapping("/categories/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> updateCategoryStatus(@PathVariable Long categoryId, @RequestParam("isEnabled") boolean isEnabled) {
        categoryService.updateCategoryStatus(categoryId, isEnabled);
        return ResponseEntity.ok(ApiResponse.ok("Category Status Updated Successfully"));
    }


    @PatchMapping("/categories/{categoryId}/keywords/{keywordName}")
    public ResponseEntity<ApiResponse<Void>> updateKeywordStatus(@PathVariable Long categoryId, @PathVariable String keywordName, @RequestParam("isEnabled") boolean isEnabled) {
        keywordService.updateKeywordStatus(categoryId, keywordName, isEnabled);
        return ResponseEntity.ok(ApiResponse.ok("Keyword Status Updated Successfully"));
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long categoryId) {
        categoryService.delete(categoryId);
        return ResponseEntity.ok(ApiResponse.ok("Category deleted Successfully"));
    }

    @DeleteMapping("/categories/{categoryId}/keywords/{keywordName}")
    public ResponseEntity<ApiResponse<Void>> deleteKeyword(@PathVariable Long categoryId, @PathVariable String keywordName) {
        keywordService.deleteKeywordByCategoryIdAndKeywordName(categoryId, keywordName);
        return ResponseEntity.ok(ApiResponse.ok("Keyword deleted Successfully"));
    }


    @GetMapping("/news-sources")
    public ResponseEntity<ApiResponse<List<NewsSourceDTO>>> getAllNewsSources() {
        return ResponseEntity.ok(ApiResponse.ok("News source Received Successfully", newsSourceService.getAll()));
    }

    @PostMapping("/news-sources")
    public ResponseEntity<ApiResponse<NewsSourceDTO>> createNewsSource(@Valid @RequestBody NewsSourceDTO dto) {
        return ResponseEntity.ok(ApiResponse.ok("News source created Successfully", newsSourceService.create(dto)));
    }

    @PutMapping("/news-sources/{id}")
    public ResponseEntity<ApiResponse<NewsSourceDTO>> updateNewsSource(
            @PathVariable Long id, @Valid @RequestBody NewsSourceDTO dto) {
        return ResponseEntity.ok(ApiResponse.ok("News source updated Successfully", newsSourceService.update(id, dto)));
    }

    @PatchMapping("/news-sources/{id}/apikey")
    public ResponseEntity<ApiResponse<Void>> updateSourceApiKey(
            @PathVariable("id") Long sourceId,
            @Valid @NotNull(message = "ApiKey Cannot be null or empty") @RequestParam("newApiKey") String newApiKey) {

        newsSourceService.updateSourceApiKeyById(sourceId, newApiKey);
        return ResponseEntity.ok(ApiResponse.ok("API key updated successfully"));
    }


    @PutMapping("/news-sources")
    public ResponseEntity<ApiResponse<List<NewsSourceDTO>>> newsSources() {
        return ResponseEntity.ok(ApiResponse.ok(newsSourceService.getAllByEnabledAndUpdateLastModified()));
    }

    @GetMapping("/news-sources/{id}")
    public ResponseEntity<ApiResponse<NewsSourceDTO>> getNewsSourceById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("News source Received Successfully", newsSourceService.getById(id)));
    }

    @PatchMapping("/articles/{id}/status")
    public ResponseEntity<ApiResponse<Void>> updateArticleStatus(@PathVariable Long id, @RequestParam("isEnabled") boolean isEnabled) {

        newsAggregationService.updateArticleStatusById(id, isEnabled);
        return ResponseEntity.ok(ApiResponse.ok("Article " + (isEnabled ? "enabled" : "hidden") + "Successfully"));
    }

    @GetMapping("/reported-articles")
    public ResponseEntity<ApiResponse<List<ArticleDTO>>> getReportedArticles() {
        return ResponseEntity.ok(ApiResponse.ok(newsAggregationService.getAllReportedArticles()));
    }

    @GetMapping("/{articleId}/article-reports")
    public ResponseEntity<ApiResponse<List<ArticleReportDTO>>> getArticleReports(@PathVariable Long articleId) {
        return ResponseEntity.ok(ApiResponse.ok(newsAggregationService.getAllArticleReportsByArticleId(articleId)));
    }


}
