package com.news_aggregation_system.service.news;

import com.news_aggregation_system.dto.ArticleDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ExternalNewsFetcherServiceImplTest {

    @InjectMocks
    private ExternalNewsFetcherServiceImpl service;


    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("fetchAndConvert - success")
    void fetchAndConvert_success() {
        String url = "abc.ccom";
        String apiKey = "ghjk";
        List<ArticleDTO> result = service.fetchAndConvert(url, apiKey);
        assertThat(result).isNotNull();

    }

}