package com.news_aggregation_system.controller;

import com.news_aggregation_system.dto.CategoryDTO;
import com.news_aggregation_system.dto.NewsSourceDTO;
import com.news_aggregation_system.response.ApiResponse;
import com.news_aggregation_system.service.admin.CategoryService;
import com.news_aggregation_system.service.admin.NewsSourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final NewsSourceService newsSourceService;
    private final CategoryService categoryService;


    public AdminController(NewsSourceService newsSourceService, CategoryService categoryService) {
        this.newsSourceService = newsSourceService;
        this.categoryService = categoryService;
    }

    @PostMapping("/categories")
    public ResponseEntity<ApiResponse<CategoryDTO>> createCategory(@RequestBody CategoryDTO dto) {
        return ResponseEntity.ok(ApiResponse.ok("Category created", categoryService.create(dto)));
    }

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getCategories() {
        return ResponseEntity.ok(ApiResponse.ok(categoryService.getAll()));
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Category deleted"));
    }

    @PostMapping("/news-sources")
    public ResponseEntity<ApiResponse<NewsSourceDTO>> create(@RequestBody NewsSourceDTO dto) {
        return ResponseEntity.ok(ApiResponse.ok("News source created", newsSourceService.create(dto)));
    }

    @PutMapping("/news-sources/{id}")
    public ResponseEntity<ApiResponse<NewsSourceDTO>> updateNewsSource(
            @PathVariable Long id, @RequestBody NewsSourceDTO dto) {
        return ResponseEntity.ok(ApiResponse.ok("News source updated", newsSourceService.update(id, dto)));
    }


    @PutMapping("/news-sources")
    public ResponseEntity<ApiResponse<List<NewsSourceDTO>>> newsSources() {
        return ResponseEntity.ok(ApiResponse.ok(newsSourceService.getAllByEnabledAndUpdateLastModified()));
    }

}
