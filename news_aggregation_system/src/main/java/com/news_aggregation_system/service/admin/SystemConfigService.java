package com.news_aggregation_system.service.admin;

public interface SystemConfigService {
    int getCurrentReportThreshold();

    void updateThreshold(int newThreshold);
}
