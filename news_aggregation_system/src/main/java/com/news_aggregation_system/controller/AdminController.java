package com.news_aggregation_system.controller;

import com.news_aggregation_system.dto.CategoryDTO;
import com.news_aggregation_system.dto.NewsSourceDTO;
import com.news_aggregation_system.response.ApiResponse;
import com.news_aggregation_system.service.admin.CategoryService;
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
    private final NewsAggregationService newsAggregationService;

    public AdminController(NewsSourceService newsSourceService, CategoryService categoryService, NewsAggregationService newsAggregationService) {
        this.newsSourceService = newsSourceService;
        this.categoryService = categoryService;
        this.newsAggregationService = newsAggregationService;
    }

    @PostMapping("/categories")
    public ResponseEntity<ApiResponse<CategoryDTO>> createCategory(@Valid @RequestBody CategoryDTO dto) {
        return ResponseEntity.ok(ApiResponse.ok("Category created Successfully", categoryService.create(dto)));
    }

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getCategories() {
        return ResponseEntity.ok(ApiResponse.ok(categoryService.getAll()));
    }

    @PatchMapping("/categories/{id}")
    public ResponseEntity<ApiResponse<Void>> updateCategoryStatus(@PathVariable Long id, @RequestParam("isEnabled") boolean isEnabled) {
        categoryService.updateCategoryStatus(id, isEnabled);
        return ResponseEntity.ok(ApiResponse.ok("Category Status Updated Successfully"));
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Category deleted Successfully"));
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


}
