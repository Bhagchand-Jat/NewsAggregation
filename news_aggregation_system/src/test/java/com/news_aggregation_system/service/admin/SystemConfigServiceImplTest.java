package com.news_aggregation_system.service.admin;

import com.news_aggregation_system.repository.ArticleRepository;
import com.news_aggregation_system.repository.SystemConfigRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.*;

public class SystemConfigServiceImplTest {

    @InjectMocks
    private SystemConfigServiceImpl service;

    @Mock
    private SystemConfigRepository systemConfigRepository;

    @Mock
    private ArticleRepository articleRepository;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("getCurrentReportThreshold - success")
    void getCurrentReportThreshold_success() {
        
        assertThatNoException().isThrownBy(()->service.getCurrentReportThreshold());
    }

    @Test
    @DisplayName("updateThreshold - success")
    void updateThreshold_success() {

        int newThreshold = 1;
        assertThatNoException().isThrownBy(() -> service.updateThreshold(newThreshold));
     

    }

}