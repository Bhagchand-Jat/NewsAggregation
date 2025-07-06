package com.news_aggregation_system.controller;

import com.news_aggregation_system.dto.CategoryStatusDTO;
import com.news_aggregation_system.dto.KeywordListRequest;
import com.news_aggregation_system.response.ApiResponse;
import com.news_aggregation_system.security.CustomUserDetails;
import com.news_aggregation_system.service.user.CategoryPreferenceService;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static com.news_aggregation_system.service.common.Constant.*;


@RestController
@RequestMapping("/api/users/me/notifications/preferences")
@PreAuthorize("hasRole('USER')")
public class PreferenceController {

    private final CategoryPreferenceService categoryPreferenceService;


    public PreferenceController(CategoryPreferenceService categoryPreferenceService) {
        this.categoryPreferenceService = categoryPreferenceService;

    }


    @PostMapping("/categories/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> enableCategoryPreference(
            @AuthenticationPrincipal CustomUserDetails user, @PathVariable Long categoryId) {
        categoryPreferenceService.enableCategoryForUser(user.getUserId(), categoryId);
        return ResponseEntity.ok(ApiResponse.ok(CATEGORY_ENABLED_SUCCESS));
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> disableCategoryPreference(
            @AuthenticationPrincipal CustomUserDetails user, @PathVariable Long categoryId) {
        categoryPreferenceService.disableCategoryForUser(user.getUserId(), categoryId);
        return ResponseEntity.ok(ApiResponse.ok(CATEGORY_DISABLED_SUCCESS));
    }


    @PostMapping("/categories/{categoryId}/keywords")
    public ResponseEntity<ApiResponse<Void>> addKeywordsToCategory(
            @AuthenticationPrincipal CustomUserDetails user, @PathVariable Long categoryId, @Valid @RequestBody KeywordListRequest request) {
        categoryPreferenceService.addKeywordsToCategory(user.getUserId(), categoryId, request.getKeywords());
        return ResponseEntity.ok(ApiResponse.ok(KEYWORDS_ADDED_SUCCESS));
    }

    @DeleteMapping("/categories/{categoryId}/keywords")
    public ResponseEntity<ApiResponse<Void>> deleteKeyword(
            @AuthenticationPrincipal CustomUserDetails user, @PathVariable Long categoryId, @Param("keyword") String keyword) {
        categoryPreferenceService.deleteKeywordFromCategory(user.getUserId(), categoryId, keyword);
        return ResponseEntity.ok(ApiResponse.ok(KEYWORD_DELETE_SUCCESS));
    }

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<CategoryStatusDTO>>> getAllEnabledCategoriesWithStatus(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(ApiResponse.ok(categoryPreferenceService.getEnabledCategoriesStatus(user.getUserId())));
    }

    @GetMapping("/categories/{categoryId}/keywords")
    public ResponseEntity<ApiResponse<List<String>>> getAllEnabledKeywordsForCategory(@AuthenticationPrincipal CustomUserDetails user, @PathVariable Long categoryId) {
        return ResponseEntity.ok(ApiResponse.ok(categoryPreferenceService.getEnabledKeywordsForCategory(user.getUserId(), categoryId)));
    }

    @GetMapping("/keywords")
    public ResponseEntity<ApiResponse<Set<String>>> getAllEnabledKeywords(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(ApiResponse.ok(categoryPreferenceService.getEnabledKeywords(user.getUserId())));
    }

}