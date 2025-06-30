package com.newsaggregator.client.dto;

public class CategoryDTO {
    private Long categoryId;

    private String name;

    private Long userId;
    private boolean enabled = true;


    public CategoryDTO(String name, Long userId) {
        this.name = name;
        this.userId = userId;
    }


    public CategoryDTO(Long categoryId, boolean enabled) {
        this.categoryId = categoryId;
        this.enabled = enabled;
    }


    public CategoryDTO(Long categoryId, String name, Long userId) {
        this.categoryId = categoryId;
        this.name = name;
        this.userId = userId;
    }


    public CategoryDTO() {
    }

    public CategoryDTO(String categoryName) {
        this.name = categoryName;
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
