package com.news_aggregation_system.service.news;

import com.news_aggregation_system.dto.ArticleDTO;
import com.news_aggregation_system.dto.ArticleFilterRequestDTO;
import com.news_aggregation_system.dto.ArticleReportDTO;
import com.news_aggregation_system.repository.ArticleReportRepository;
import com.news_aggregation_system.repository.ArticleRepository;
import com.news_aggregation_system.service.admin.KeywordService;
import com.news_aggregation_system.service.admin.NewsSourceService;
import com.news_aggregation_system.service.admin.SystemConfigService;
import com.news_aggregation_system.service.user.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class NewsAggregationServiceImplTest {

    @InjectMocks
    private NewsAggregationServiceImpl service;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private ArticleReportRepository articleReportRepository;

    @Mock
    private NewsSourceService newsSourceService;

    @Mock
    private KeywordService keywordService;

    @Mock
    private UserService userService;

    @Mock
    private SystemConfigService systemConfigService;

    @Mock
    private CategoryPreferenceService categoryPreferenceService;

    @Mock
    private ArticleReactionService articleReactionService;

    @Mock
    private SavedArticleService savedArticleService;

    @Mock
    private ArticleReadHistoryService articleReadHistoryService;



    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("fetchExternalNews - success")
    void fetchExternalNews_success() {
        List<ArticleDTO> result = service.fetchExternalNews();
        assertThat(result).isNotNull();

    }


    @Test
    @DisplayName("saveAllArticles - success")
    void saveAllArticles_success() {
        List<ArticleDTO> articleDTOs = new ArrayList<>(); 
        List<ArticleDTO> result = service.saveAllArticles(articleDTOs);
        assertThat(result).isNotNull();

    }


    @Test
    @DisplayName("updateArticleStatusById - success")
    void updateArticleStatusById_success() {
        Long articleId = 1L;
        boolean enabled = false;

       assertThatThrownBy(()-> service.updateArticleStatusById(articleId, enabled));
       
    }

    @Test
    @DisplayName("reportArticle - success")
    void reportArticle_success() {
        ArticleReportDTO articleReportDTO = new ArticleReportDTO(); 
        service.reportArticle(articleReportDTO);
       
    }

    @Test
    @DisplayName("getAllArticleReportsByArticleId - success")
    void getAllArticleReportsByArticleId_success() {
        Long articleId = 0L;
        List<ArticleReportDTO> result = service.getAllArticleReportsByArticleId(articleId);
        assertThat(result).isNotNull();
       
    }

    @Test
    @DisplayName("getArticleReportByArticleIdAndUserId - success")
    void getArticleReportByArticleIdAndUserId_success() {
        Long userId = 2L;
        List<ArticleReportDTO> result = service.getAllArticlesReportsByUserId( userId);
        assertThat(result).isNotNull();
       
    }

    @Test
    @DisplayName("getAllArticlesReportsByUserId - success")
    void getAllArticlesReportsByUserId_success() {
        Long userId = 1L;
        List<ArticleReportDTO> result = service.getAllArticlesReportsByUserId(userId);
        assertThat(result).isNotNull();
       
    }

    @Test
    @DisplayName("filterArticles - success")
    void filterArticles_success() {
        ArticleFilterRequestDTO articleFilterRequestDTO = new ArticleFilterRequestDTO();
        articleFilterRequestDTO.setDate(new Date());
        Long userId=0L;
        List<ArticleDTO> result = service.filterArticles(userId,articleFilterRequestDTO);
        assertThat(result).isNotNull();
       
    }

}