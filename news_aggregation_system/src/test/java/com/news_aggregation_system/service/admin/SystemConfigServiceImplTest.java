package com.news_aggregation_system.service.admin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SystemConfigServiceImplTest {

    @InjectMocks
    private SystemConfigServiceImpl service;


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

        int newThreshold = 0;   
        assertThatNoException().isThrownBy(() -> service.updateThreshold(newThreshold));
     

    }

}