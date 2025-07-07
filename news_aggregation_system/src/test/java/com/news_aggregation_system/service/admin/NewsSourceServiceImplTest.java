package com.news_aggregation_system.service.admin;

import com.news_aggregation_system.dto.NewsSourceDTO;
import com.news_aggregation_system.repository.NewsSourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class NewsSourceServiceImplTest {

    @InjectMocks
    private NewsSourceServiceImpl service;

    @Mock
    private NewsSourceRepository newsSourceRepository;


    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("findAllByEnabled - success")
    void findAllByEnabled_success() {
        List<NewsSourceDTO> result = service.getAll();
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("getAllByEnabledAndUpdateLastModified - success")
    void getAllByEnabledAndUpdateLastModified_success() {
        List<NewsSourceDTO> result = service.getAllByEnabledAndUpdateLastModified();
        assertThat(result).isNotNull();
     
    }

    @Test
    @DisplayName("updateSourceApiKeyById - success")
    void updateSourceApiKeyById_success() {
        Long sourceId = 0L;
        String sourceApiKey = "ghjk";
        assertThatThrownBy(() -> service.updateSourceApiKeyById(sourceId, sourceApiKey));

    }

}