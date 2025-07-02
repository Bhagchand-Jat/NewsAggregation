package com.news_aggregation_system.dto;

import com.news_aggregation_system.model.Keyword;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public class CategoryDTO {
    private Long categoryId;

    @NotBlank(message = "Category name is required")
    private String name;

    private boolean enabled = true;

    private Set<Keyword> keywords;


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


    public boolean isEnabled() {
        return enabled;
    }


    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<Keyword> keywords) {
        this.keywords = keywords;
    }
}
