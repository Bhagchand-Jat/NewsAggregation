package com.news_aggregation_system.service.admin;

import com.news_aggregation_system.dto.KeywordDTO;
import com.news_aggregation_system.exception.NotFoundException;
import com.news_aggregation_system.model.Keyword;
import com.news_aggregation_system.repository.CategoryRepository;
import com.news_aggregation_system.repository.KeywordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class KeywordServiceImplTest {

    @InjectMocks
    private KeywordServiceImpl service;

    @Mock
    private KeywordRepository keywordRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("creteKeyword - success")
    void creteKeyword_success() {
        KeywordDTO dto = new KeywordDTO();
        dto.setName("test");
        Keyword keyword = new Keyword();
        keyword.setName("test");

        when(keywordRepository.save(any())).thenReturn(keyword);

        KeywordDTO result = service.creteKeyword(dto);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("test");
    }

    @Test
    @DisplayName("deleteKeywordByCategoryIdAndKeywordName - success")
    void deleteKeyword_success() {
        when(keywordRepository.deleteKeywordByCategoryCategoryIdAndNameContainingIgnoreCase(1L, "test"))
                .thenReturn(1);

        service.deleteKeywordByCategoryIdAndKeywordName(1L, "test");

        verify(keywordRepository, times(1)).deleteKeywordByCategoryCategoryIdAndNameContainingIgnoreCase(1L, "test");
    }

    @Test
    @DisplayName("deleteKeywordByCategoryIdAndKeywordName - failure")
    void deleteKeyword_failure() {
        when(keywordRepository.deleteKeywordByCategoryCategoryIdAndNameContainingIgnoreCase(1L, "test"))
                .thenReturn(0);

        assertThatThrownBy(() -> service.deleteKeywordByCategoryIdAndKeywordName(1L, "test"))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("getAllKeywordsByCategory - success")
    void getAllKeywordsByCategory_success() {
        when(keywordRepository.getKeywordsByCategoryCategoryId(1L)).thenReturn(List.of(new Keyword()));

        List<KeywordDTO> result = service.getAllKeywordsByCategory(1L);

        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("updateKeywordStatus - success")
    void updateKeywordStatus_success() {
        when(keywordRepository.updateEnabledByCategoryCategoryIdAndNameContainingIgnoreCase(true, 1L, "test"))
                .thenReturn(1);

        service.updateKeywordStatus(1L, "test", true);
    }

    @Test
    @DisplayName("updateKeywordStatus - failure")
    void updateKeywordStatus_failure() {
        when(keywordRepository.updateEnabledByCategoryCategoryIdAndNameContainingIgnoreCase(true, 1L, "test"))
                .thenReturn(0);

        assertThatThrownBy(() -> service.updateKeywordStatus(1L, "test", true))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("addKeywordsToCategory - success")
    void addKeywordsToCategory_success() {
        when(categoryRepository.existsById(1L)).thenReturn(true);
        when(keywordRepository.existsByCategoryCategoryIdAndNameContainsIgnoreCase(eq(1L), anyString())).thenReturn(false);

        List<String> keywords = List.of("Java", "Spring");

        assertThatNoException().isThrownBy(() -> service.addKeywordsToCategory(1L, keywords));

    }

    @Test
    @DisplayName("addKeywordsToCategory - failure: category not found")
    void addKeywordsToCategory_categoryNotFound() {
        when(categoryRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> service.addKeywordsToCategory(1L, List.of("Java")))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("findByEnabledFalse - success")
    void findByEnabledFalse_success() {
        when(keywordRepository.findByEnabledFalse()).thenReturn(Collections.singletonList(new Keyword()));

        List<KeywordDTO> result = service.findByEnabledFalse();

        assertThat(result).isNotNull();
    }
}
