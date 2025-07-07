package com.news_aggregation_system.service.user;

import com.news_aggregation_system.dto.ArticleDTO;
import com.news_aggregation_system.dto.SavedArticleDTO;
import com.news_aggregation_system.mapper.ArticleMapper;
import com.news_aggregation_system.model.Article;
import com.news_aggregation_system.model.SavedArticle;
import com.news_aggregation_system.repository.SavedArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SavedArticleServiceImplTest {

    @Mock SavedArticleRepository savedArticleRepository;
    @Mock ArticleMapper mapper;
    @InjectMocks SavedArticleServiceImpl service;

    @Test
    @DisplayName("getSavedArticlesByUser → returns list")
    void getSavedArticlesByUser_success() {
        Article article = new Article();
        article.setArticleId(1L);
        SavedArticle sa = new SavedArticle();
        sa.setArticle(article);
        when(savedArticleRepository.findByUserUserId(5L)).thenReturn(List.of(sa));
        ArticleDTO dto = new ArticleDTO();
        dto.setArticleId(1L);
        when(ArticleMapper.toDto(article)).thenReturn(dto);
        List<ArticleDTO> result = service.getSavedArticlesByUser(5L);
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getArticleId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("saveArticle → persists entity")
    void saveArticle_success() {
        SavedArticleDTO dto = new SavedArticleDTO();
        dto.setArticleId(2L);
        dto.setUserId(7L);
        service.saveArticle(dto);
        verify(savedArticleRepository).save(any(SavedArticle.class));
    }

    @Test
    @DisplayName("deleteSavedArticle → deletes entity")
    void deleteSavedArticle_success() {
        SavedArticle savedArticle = new SavedArticle();
        when(savedArticleRepository.findByUserUserId(7L)).thenReturn(List.of());
        service.deleteSavedArticle(7L, 2L);
        verify(savedArticleRepository).delete(savedArticle);
    }
}
