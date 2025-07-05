package com.news_aggregation_system.controller;

import com.news_aggregation_system.dto.*;
import com.news_aggregation_system.response.ApiResponse;
import com.news_aggregation_system.service.admin.CategoryService;
import com.news_aggregation_system.service.news.NewsAggregationService;
import com.news_aggregation_system.service.user.ArticleReadHistoryService;
import com.news_aggregation_system.service.user.SavedArticleService;
import com.news_aggregation_system.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.news_aggregation_system.service.common.Constant.*;

@RestController
@RequestMapping("/api/users")
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

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(@PathVariable Long userId, @Valid @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.update(userId, userDTO);
        ApiResponse<UserDTO> response = new ApiResponse<>(USER_UPDATED_SUCCESS, true, updatedUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable Long userId) {
        UserDTO user = userService.getById(userId);
        ApiResponse<UserDTO> response = new ApiResponse<>(USER_FETCHED_SUCCESS, true, user);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {

        return ResponseEntity.ok(ApiResponse.ok(userService.getAll()));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
        ApiResponse<Void> response = new ApiResponse<>(USER_DELETE_SUCCESS, true, null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/saved-articles")
    public ResponseEntity<ApiResponse<List<ArticleDTO>>> getSavedArticles(@PathVariable Long userId) {
        List<ArticleDTO> saved = savedArticleService.getSavedArticlesByUser(userId);
        return ResponseEntity.ok(ApiResponse.ok(saved));
    }

    @PostMapping("/save-article")
    public ResponseEntity<ApiResponse<Void>> saveArticle(@Valid @RequestBody SavedArticleDTO savedArticleDTO) {
        savedArticleService.saveArticle(savedArticleDTO);
        return ResponseEntity.ok(ApiResponse.ok(ARTICLE_SAVED_SUCCESS));
    }

    @DeleteMapping("/{userId}/saved-article/{articleId}")
    public ResponseEntity<ApiResponse<Void>> deleteSavedArticle(
            @PathVariable Long userId,
            @PathVariable Long articleId) {
        savedArticleService.deleteSavedArticle(userId, articleId);
        return ResponseEntity.ok(ApiResponse.ok(SAVED_ARTICLE_DELETE_SUCCESS));
    }

    @GetMapping("/categories/enabled")
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getAllEnabledCategories() {
        return ResponseEntity.ok(ApiResponse.ok(categoryService.getEnabledCategories()));
    }

    @PostMapping("/report-article")
    public ResponseEntity<ApiResponse<Void>> reportArticle(@RequestBody @Valid ArticleReportDTO articleReportDTO) {
        newsAggregationService.reportArticle(articleReportDTO);
        return ResponseEntity.ok(ApiResponse.ok(ARTICLE_REPORTED_SUCCESS));
    }

    @GetMapping("/{userId}/articles-reports")
    public ResponseEntity<ApiResponse<List<ArticleReportDTO>>> getArticlesReports(@PathVariable Long userId) {

        return ResponseEntity.ok(ApiResponse.ok(newsAggregationService.getAllArticlesReportsByUserId(userId)));
    }

    @PostMapping("/{userId}/article/{articleId}/markAsRead")
    public ResponseEntity<ApiResponse<Void>> createReadArticleHistory(@PathVariable Long userId, @PathVariable Long articleId) {
        articleReadHistoryService.markAsRead(userId, articleId);
        return ResponseEntity.ok(ApiResponse.ok(ARTICLE_READ_HISTORY_UPDATED_SUCCESS));
    }

    @GetMapping("/{userId}/articles-read-history")
    public ResponseEntity<ApiResponse<List<ArticleReadHistoryDTO>>> getArticlesReadHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.ok(articleReadHistoryService.getArticleReadHistory(userId)));
    }

}

