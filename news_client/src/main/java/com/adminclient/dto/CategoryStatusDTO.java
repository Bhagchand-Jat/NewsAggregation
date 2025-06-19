package com.adminclient.dto;

public class CategoryStatusDTO {
    private Long categoryId;
    private String name;
    private boolean enabled;

    public CategoryStatusDTO() {
    }

    public CategoryStatusDTO(Long id, String name, boolean enabled) {
        this.categoryId = id;
        this.name = name;
        this.enabled = enabled;
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

}
