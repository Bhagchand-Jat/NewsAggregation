package com.news_aggregation_system.controller;

import com.news_aggregation_system.dto.ArticleDTO;
import com.news_aggregation_system.dto.CategoryDTO;
import com.news_aggregation_system.dto.UserDTO;
import com.news_aggregation_system.response.ApiResponse;
import com.news_aggregation_system.service.admin.CategoryService;
import com.news_aggregation_system.service.news.NewsAggregationService;
import com.news_aggregation_system.service.user.SavedArticleService;
import com.news_aggregation_system.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final SavedArticleService savedArticleService;
    private final CategoryService categoryService;
    private final NewsAggregationService newsAggregationService;

    public UserController(UserService userService, SavedArticleService savedArticleService, CategoryService categoryService, NewsAggregationService newsAggregationService) {
        this.userService = userService;
        this.savedArticleService = savedArticleService;
        this.categoryService = categoryService;
        this.newsAggregationService = newsAggregationService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.update(id, userDTO);
        ApiResponse<UserDTO> response = new ApiResponse<>("User updated successfully", true, updatedUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getById(id);
        ApiResponse<UserDTO> response = new ApiResponse<>("User fetched successfully", true, user);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {
        List<UserDTO> users = userService.getAll();
        ApiResponse<List<UserDTO>> response = new ApiResponse<>("All users fetched successfully", true, users);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        ApiResponse<Void> response = new ApiResponse<>("User deleted successfully", true, null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/saved-articles")
    public ResponseEntity<ApiResponse<List<ArticleDTO>>> getSavedArticles(@PathVariable Long userId) {
        List<ArticleDTO> saved = savedArticleService.getSavedArticlesByUser(userId);
        return ResponseEntity.ok(ApiResponse.ok("Fetched saved articles", saved));
    }

    @PostMapping("/{userId}/save-article/{articleId}")
    public ResponseEntity<ApiResponse<Void>> saveArticle(
            @PathVariable Long userId,
            @PathVariable Long articleId) {
        savedArticleService.saveArticle(userId, articleId);
        return ResponseEntity.ok(ApiResponse.ok("Article saved successfully"));
    }

    @GetMapping("/categories/enabled")
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getAllEnabledCategories() {
        return ResponseEntity.ok(ApiResponse.ok(categoryService.getEnabledCategories()));
    }

    @PostMapping("/articles/{articleId}/report")
    public ResponseEntity<ApiResponse<Void>> reportArticle(@PathVariable Long articleId, @RequestParam Long userId, @RequestParam String reason) {
        newsAggregationService.reportArticle(articleId, userId, reason);
        return ResponseEntity.ok(ApiResponse.ok("Article reported successfully"));
    }

}

