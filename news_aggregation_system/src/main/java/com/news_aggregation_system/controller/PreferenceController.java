package com.news_aggregation_system.controller;

import com.news_aggregation_system.dto.CategoryStatusDTO;
import com.news_aggregation_system.dto.KeywordDTO;
import com.news_aggregation_system.response.ApiResponse;
import com.news_aggregation_system.service.user.CategoryPreferenceService;
import com.news_aggregation_system.service.user.KeywordService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users/{userId}/notifications/preferences")
public class PreferenceController {

    private final CategoryPreferenceService categoryPreferenceService;
    private final KeywordService keywordService;


    public PreferenceController(CategoryPreferenceService categoryPreferenceService, KeywordService keywordService) {
        this.categoryPreferenceService = categoryPreferenceService;
        this.keywordService = keywordService;
    }


    @PostMapping("/categories/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> enableCategoryPreference(
            @PathVariable Long userId, @PathVariable Long categoryId) {
        categoryPreferenceService.createPreference(userId, categoryId, true);
        return ResponseEntity.ok(ApiResponse.ok("Category enabled"));
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> disableCategoryPreference(
            @PathVariable Long userId, @PathVariable Long categoryId) {
        categoryPreferenceService.deletePreference(userId, categoryId);
        return ResponseEntity.ok(ApiResponse.ok("Category disabled"));
    }


    @PostMapping("/keywords")
    public ResponseEntity<ApiResponse<KeywordDTO>> addKeyword(
            @PathVariable Long userId, @Valid @RequestBody KeywordDTO dto) {
        KeywordDTO keywordDTO = keywordService.creteKeyword(userId, dto);
        return ResponseEntity.ok(new ApiResponse<>("Keyword Added successfully", true, keywordDTO));
    }

    @DeleteMapping("/keywords/{keywordId}")
    public ResponseEntity<ApiResponse<Void>> deleteKeyword(
            @PathVariable Long userId, @PathVariable Long keywordId) {
        keywordService.deleteKeywordByIdAndUserId(userId, keywordId);
        return ResponseEntity.ok(ApiResponse.ok("Keyword deleted Successfully"));
    }

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<CategoryStatusDTO>>> getAllEnabledCategoriesWithStatus(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.ok(categoryPreferenceService.getEnabledCategoriesStatus(userId)));
    }
}
