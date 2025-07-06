package com.news_aggregation_system.controller;

import com.news_aggregation_system.dto.*;
import com.news_aggregation_system.response.ApiResponse;
import com.news_aggregation_system.security.CustomUserDetails;
import com.news_aggregation_system.service.admin.CategoryService;
import com.news_aggregation_system.service.news.NewsAggregationService;
import com.news_aggregation_system.service.user.ArticleReadHistoryService;
import com.news_aggregation_system.service.user.SavedArticleService;
import com.news_aggregation_system.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.news_aggregation_system.service.common.Constant.*;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('USER')")
public class UserController {

    private final UserService userService;
    private final SavedArticleService savedArticleService;
    private final CategoryService categoryService;
    private final NewsAggregationService newsAggregationService;
    private final ArticleReadHistoryService articleReadHistoryService;

    public UserController(UserService userService, SavedArticleService savedArticleService, CategoryService categoryService, NewsAggregationService newsAggregationService, ArticleReadHistoryService articleReadHistoryService) {
        this.userService = userService;
        this.savedArticleService = savedArticleService;
        this.categoryService = categoryService;
        this.newsAggregationService = newsAggregationService;
        this.articleReadHistoryService = articleReadHistoryService;
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(@AuthenticationPrincipal CustomUserDetails user, @Valid @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.update(user.getUserId(), userDTO);
        ApiResponse<UserDTO> response = new ApiResponse<>(USER_UPDATED_SUCCESS, true, updatedUser);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@AuthenticationPrincipal CustomUserDetails user) {
        userService.delete(user.getUserId());
        ApiResponse<Void> response = new ApiResponse<>(USER_DELETE_SUCCESS, true, null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me/saved-articles")
    public ResponseEntity<ApiResponse<List<ArticleDTO>>> getSavedArticles(@AuthenticationPrincipal CustomUserDetails user) {
        List<ArticleDTO> saved = savedArticleService.getSavedArticlesByUser(user.getUserId());
        return ResponseEntity.ok(ApiResponse.ok(saved));
    }

    @PostMapping("/save-article")
    public ResponseEntity<ApiResponse<Void>> saveArticle(@Valid @RequestBody SavedArticleDTO savedArticleDTO) {
        savedArticleService.saveArticle(savedArticleDTO);
        return ResponseEntity.ok(ApiResponse.ok(ARTICLE_SAVED_SUCCESS));
    }

    @DeleteMapping("/me/saved-article/{articleId}")
    public ResponseEntity<ApiResponse<Void>> deleteSavedArticle(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long articleId) {
        savedArticleService.deleteSavedArticle(user.getUserId(), articleId);
        return ResponseEntity.ok(ApiResponse.ok(SAVED_ARTICLE_DELETE_SUCCESS));
    }

    @GetMapping("/categories/enabled")
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getAllEnabledCategories() {
        return ResponseEntity.ok(ApiResponse.ok(categoryService.getEnabledCategories()));
    }

    @PostMapping("/report-article")
    public ResponseEntity<ApiResponse<Void>> reportArticle(@AuthenticationPrincipal CustomUserDetails user,@RequestBody @Valid ArticleReportDTO articleReportDTO) {
        articleReportDTO.setUserId(user.getUserId());
        newsAggregationService.reportArticle(articleReportDTO);
        return ResponseEntity.ok(ApiResponse.ok(ARTICLE_REPORTED_SUCCESS));
    }

    @GetMapping("/me/articles-reports")
    public ResponseEntity<ApiResponse<List<ArticleReportDTO>>> getArticlesReports(@AuthenticationPrincipal CustomUserDetails user) {

        return ResponseEntity.ok(ApiResponse.ok(newsAggregationService.getAllArticlesReportsByUserId(user.getUserId())));
    }

    @PostMapping("/me/article/{articleId}/markAsRead")
    public ResponseEntity<ApiResponse<Void>> createReadArticleHistory(@AuthenticationPrincipal CustomUserDetails user, @PathVariable Long articleId) {
        articleReadHistoryService.markAsRead(user.getUserId(), articleId);
        return ResponseEntity.ok(ApiResponse.ok(ARTICLE_READ_HISTORY_UPDATED_SUCCESS));
    }

    @GetMapping("/me/articles-read-history")
    public ResponseEntity<ApiResponse<List<ArticleReadHistoryDTO>>> getArticlesReadHistory(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(ApiResponse.ok(articleReadHistoryService.getArticleReadHistory(user.getUserId())));
    }

}

