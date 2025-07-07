package com.news_aggregation_system.service.user;

import com.news_aggregation_system.dto.ArticleReactionDTO;
import com.news_aggregation_system.mapper.ArticleReactionMapper;
import com.news_aggregation_system.model.Article;
import com.news_aggregation_system.model.ArticleReaction;
import com.news_aggregation_system.model.ReactionType;
import com.news_aggregation_system.model.User;
import com.news_aggregation_system.repository.ArticleReactionRepository;
import com.news_aggregation_system.repository.ArticleRepository;
import com.news_aggregation_system.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ArticleReactionServiceImplTest {

    @InjectMocks
    private ArticleReactionServiceImpl service;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ArticleReactionRepository articleReactionRepository;


    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("reactToArticle - success")
    void reactToArticle_success() {
        ArticleReactionDTO dto = new ArticleReactionDTO();
        dto.setArticleId(1L);
        dto.setUserId(7L);
        dto.setReactionType(ReactionType.DISLIKE.toString());


        Article article = new Article();
        article.setArticleId(1L);
        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));
        User user = new User();
        user.setUserId(7L);
        when(userRepository.findById(7L)).thenReturn(Optional.of(user));

        ArticleReaction articleReaction = new ArticleReaction();
        articleReaction.setId(2L);
        articleReaction.setReactionType(ReactionType.LIKE);
        articleReaction.setArticle(article);
        articleReaction.setUser(user);

        when(ArticleReactionMapper.toDto(articleReaction)).thenReturn(dto);

        ArticleReactionDTO result = service.reactToArticle(dto);
        assertThat(result).isNotNull();

    }



}