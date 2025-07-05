package com.news_aggregation_system.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryDTO {
    private Long categoryId;

    @NotBlank(message = "Category name is required")
    private String name;

    private boolean enabled = true;


    private Long userId;

    public CategoryDTO() {
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
