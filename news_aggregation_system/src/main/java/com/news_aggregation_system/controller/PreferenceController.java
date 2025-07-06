package com.news_aggregation_system.controller;

import com.news_aggregation_system.dto.CategoryStatusDTO;
import com.news_aggregation_system.dto.KeywordListRequest;
import com.news_aggregation_system.response.ApiResponse;
import com.news_aggregation_system.service.user.CategoryPreferenceService;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static com.news_aggregation_system.service.common.Constant.*;


@RestController
@RequestMapping("/api/users/{userId}/notifications/preferences")
public class PreferenceController {

    private final CategoryPreferenceService categoryPreferenceService;


    public PreferenceController(CategoryPreferenceService categoryPreferenceService) {
        this.categoryPreferenceService = categoryPreferenceService;

    }


    @PostMapping("/categories/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> enableCategoryPreference(
            @PathVariable Long userId, @PathVariable Long categoryId) {
        categoryPreferenceService.enableCategoryForUser(userId, categoryId);
        return ResponseEntity.ok(ApiResponse.ok(CATEGORY_ENABLED_SUCCESS));
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> disableCategoryPreference(
            @PathVariable Long userId, @PathVariable Long categoryId) {
        categoryPreferenceService.disableCategoryForUser(userId, categoryId);
        return ResponseEntity.ok(ApiResponse.ok(CATEGORY_DISABLED_SUCCESS));
    }


    @PostMapping("/categories/{categoryId}/keywords")
    public ResponseEntity<ApiResponse<Void>> addKeywordsToCategory(
            @PathVariable Long userId, @PathVariable Long categoryId, @Valid @RequestBody KeywordListRequest request) {
        categoryPreferenceService.addKeywordsToCategory(userId, categoryId, request.getKeywords());
        return ResponseEntity.ok(ApiResponse.ok(KEYWORDS_ADDED_SUCCESS));
    }

    @DeleteMapping("/categories/{categoryId}/keywords")
    public ResponseEntity<ApiResponse<Void>> deleteKeyword(
            @PathVariable Long userId, @PathVariable Long categoryId, @Param("keyword") String keyword) {
        categoryPreferenceService.deleteKeywordFromCategory(userId, categoryId, keyword);
        return ResponseEntity.ok(ApiResponse.ok(KEYWORD_DELETE_SUCCESS));
    }

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<CategoryStatusDTO>>> getAllEnabledCategoriesWithStatus(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.ok(categoryPreferenceService.getEnabledCategoriesStatus(userId)));
    }

    @GetMapping("/categories/{categoryId}/keywords")
    public ResponseEntity<ApiResponse<List<String>>> getAllEnabledKeywordsForCategory(@PathVariable Long userId, @PathVariable Long categoryId) {
        return ResponseEntity.ok(ApiResponse.ok(categoryPreferenceService.getEnabledKeywordsForCategory(userId, categoryId)));
    }

    @GetMapping("/keywords")
    public ResponseEntity<ApiResponse<Set<String>>> getAllEnabledKeywords(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.ok(categoryPreferenceService.getEnabledKeywords(userId)));
    }

}
