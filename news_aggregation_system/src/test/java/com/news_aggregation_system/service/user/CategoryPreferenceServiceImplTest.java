package com.news_aggregation_system.service.user;

import com.news_aggregation_system.exception.AlreadyExistsException;
import com.news_aggregation_system.exception.NotFoundException;
import com.news_aggregation_system.model.Category;
import com.news_aggregation_system.model.User;
import com.news_aggregation_system.model.UserCategoryPreference;
import com.news_aggregation_system.model.UserKeywordPreference;
import com.news_aggregation_system.repository.CategoryRepository;
import com.news_aggregation_system.repository.UserCategoryPreferenceRepository;
import com.news_aggregation_system.repository.UserKeywordPreferenceRepository;
import com.news_aggregation_system.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryPreferenceServiceImplTest {

    @Mock UserRepository userRepository;
    @Mock CategoryRepository categoryRepository;
    @Mock UserKeywordPreferenceRepository userKeywordPreferenceRepository;
    @Mock UserCategoryPreferenceRepository userCategoryPreferenceRepository;
    @InjectMocks CategoryPreferenceServiceImpl categoryPreferenceService;

    @Test
    @DisplayName("enableCategoryForUser → saves preference")
    void enableCategory_success() {
        User user = new User(); user.setUserId(1L);
        Category category = new Category(); category.setCategoryId(2L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(category));
        when(userCategoryPreferenceRepository.findByUserUserIdAndCategoryCategoryId(1L,2L)).thenReturn(Optional.empty());
        categoryPreferenceService.enableCategoryForUser(1L,2L);
        verify(userCategoryPreferenceRepository).save(any(UserCategoryPreference.class));
    }

    @Test
    @DisplayName("enableCategoryForUser → already exists")
    void enableCategory_alreadyExists() {
        when(userCategoryPreferenceRepository.findByUserUserIdAndCategoryCategoryId(1L,2L))
                .thenReturn(Optional.of(new UserCategoryPreference()));
        assertThatThrownBy(() -> categoryPreferenceService.enableCategoryForUser(1L,2L))
                .isInstanceOf(AlreadyExistsException.class);
    }

    @Test
    @DisplayName("disableCategoryForUser → deletes preference")
    void disableCategory_success() {
        when(userCategoryPreferenceRepository.deleteByUserUserIdAndCategoryCategoryId(1L,2L)).thenReturn(1);
        categoryPreferenceService.disableCategoryForUser(1L,2L);
        verify(userCategoryPreferenceRepository).deleteByUserUserIdAndCategoryCategoryId(1L,2L);
    }

    @Test
    @DisplayName("disableCategoryForUser → not found")
    void disableCategory_notFound() {
        when(userCategoryPreferenceRepository.deleteByUserUserIdAndCategoryCategoryId(1L,2L)).thenReturn(0);
        assertThatThrownBy(() -> categoryPreferenceService.disableCategoryForUser(1L,2L))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("getEnabledCategoriesStatus → returns list")
    void enabledCategoriesStatus() {
        Category category = new Category(); category.setCategoryId(2L); category.setName("Tech");
        when(categoryRepository.findByEnabledTrue()).thenReturn(List.of(category));
        when(userCategoryPreferenceRepository.findByUserUserIdAndEnabledTrue(1L))
                .thenReturn(List.of());
        assertThat(categoryPreferenceService.getEnabledCategoriesStatus(1L)).hasSize(1);
    }

    @Test
    @DisplayName("addKeywordsToCategory → saves keywords")
    void addKeywords_success() {
        User user = new User(); user.setUserId(1L);
        Category category = new Category(); category.setCategoryId(2L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(category));
        when(userKeywordPreferenceRepository.existsByUserUserIdAndCategoryCategoryIdAndKeywordIgnoreCase(anyLong(),anyLong(),anyString())).thenReturn(false);
        categoryPreferenceService.addKeywordsToCategory(1L,2L,List.of("  AI  ","AI","  "));
        verify(userKeywordPreferenceRepository).save(any(UserKeywordPreference.class));
    }

    @Test
    @DisplayName("getEnabledKeywords → returns set")
    void getEnabledKeywords() {
        UserKeywordPreference kp = new UserKeywordPreference(); kp.setKeyword("ai");
        when(userKeywordPreferenceRepository.getEnabledKeywordsByUserId(1L)).thenReturn(List.of(kp));
        assertThat(categoryPreferenceService.getEnabledKeywords(1L)).containsExactly("ai");
    }

    @Test
    @DisplayName("deleteKeywordFromCategory → success")
    void deleteKeyword_success() {
        when(userKeywordPreferenceRepository.deleteUserKeywordPreferenceByKeywordAndUserUserIdAndCategoryCategoryId("ai",1L,2L)).thenReturn(1);
        categoryPreferenceService.deleteKeywordFromCategory(1L,2L,"ai");
        verify(userKeywordPreferenceRepository).deleteUserKeywordPreferenceByKeywordAndUserUserIdAndCategoryCategoryId("ai",1L,2L);
    }

    @Test
    @DisplayName("deleteKeywordFromCategory → not found")
    void deleteKeyword_notFound() {
        when(userKeywordPreferenceRepository.deleteUserKeywordPreferenceByKeywordAndUserUserIdAndCategoryCategoryId("ai",1L,2L)).thenReturn(0);
        assertThatThrownBy(() -> categoryPreferenceService.deleteKeywordFromCategory(1L,2L,"ai"))
                .isInstanceOf(NotFoundException.class);
    }
}
