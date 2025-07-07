package com.news_aggregation_system.service.user;

import com.news_aggregation_system.dto.ArticleDTO;
import com.news_aggregation_system.dto.SavedArticleDTO;
import com.news_aggregation_system.mapper.ArticleMapper;
import com.news_aggregation_system.model.Article;
import com.news_aggregation_system.model.SavedArticle;
import com.news_aggregation_system.model.SavedArticleId;
import com.news_aggregation_system.model.User;
import com.news_aggregation_system.repository.ArticleRepository;
import com.news_aggregation_system.repository.SavedArticleRepository;
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
class SavedArticleServiceImplTest {

    @Mock SavedArticleRepository savedArticleRepository;
    @InjectMocks SavedArticleServiceImpl service;
    @Mock ArticleMapper articleMapper;
    @Mock
    ArticleRepository articleRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("getSavedArticlesByUser → returns list")
    void getSavedArticlesByUser_success() {
        Article article = new Article();
        article.setArticleId(1L);
        SavedArticle savedArticle = new SavedArticle();
        savedArticle.setArticle(article);
        when(savedArticleRepository.findByUserUserId(5L)).thenReturn(List.of(savedArticle));
        ArticleDTO dto = new ArticleDTO();
        dto.setArticleId(1L);
        when(savedArticle.getArticle()).thenReturn(article);
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
        User user = new User();
        user.setUserId(7L);
        Article article = new Article();
        article.setArticleId(1L);
        SavedArticle savedArticle = new SavedArticle();
        savedArticle.setArticle(article);
        savedArticle.setUser(user);
        when(userRepository.findById(7L)).thenReturn(Optional.of(user));
        when(articleRepository.findById(2L)).thenReturn(Optional.of(article));
        when(savedArticleRepository.save(savedArticle)).thenReturn(savedArticle);
        when(new SavedArticle().getUser()).thenReturn(user);
        when(new SavedArticle().getArticle()).thenReturn(article);
        service.saveArticle(dto);
        verify(savedArticleRepository).save(savedArticle);
    }

    @Test
    @DisplayName("deleteSavedArticle → deletes entity")
    void deleteSavedArticle_success() {

        service.deleteSavedArticle(7L, 2L);

    }
}
