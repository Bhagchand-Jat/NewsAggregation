package com.news_aggregation_system.service.admin;

import com.news_aggregation_system.dto.CategoryDTO;
import com.news_aggregation_system.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl service;


    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("getOrCreateCategory - success")
    void getOrCreateCategory_success() {
       Set<String> categories=new HashSet<>();
        Set<Category> result = service.getOrCreateCategories(categories);
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("getOrCreateCategories - success")
    void getOrCreateCategories_success() {
        Set<String> names = new HashSet<>();
        Set<Category> result = service.getOrCreateCategories(names);
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("getByCategoryId - success")
    void getByCategoryName_success() {
        Long categoryId = 0L;
        CategoryDTO result = service.getById(categoryId);
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("getEnabledCategories - success")
    void getEnabledCategories_success() {
        List<CategoryDTO> result = service.getEnabledCategories();
        assertThat(result).isNotNull();

    }

    @Test
    @DisplayName("updateCategoryStatus - success")
    void updateCategoryStatus_success() {
        Long categoryId = 10L;
        boolean isEnabled = false;
        service.updateCategoryStatus(categoryId, isEnabled);

    }

}