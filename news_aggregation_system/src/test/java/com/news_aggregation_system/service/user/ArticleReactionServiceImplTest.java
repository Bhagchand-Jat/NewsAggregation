package com.news_aggregation_system.service.user;

import com.news_aggregation_system.dto.ArticleReactionDTO;
import com.news_aggregation_system.model.ReactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ArticleReactionServiceImplTest {

    @InjectMocks
    private ArticleReactionServiceImpl service;


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
        ArticleReactionDTO result = service.reactToArticle(dto);
        assertThat(result).isNotNull();

    }



}