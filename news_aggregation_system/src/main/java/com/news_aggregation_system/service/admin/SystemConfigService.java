package com.news_aggregation_system.service.admin;

import com.news_aggregation_system.model.SystemConfig;

public interface SystemConfigService {
    public int getCurrentReportThreshold();
    public SystemConfig updateThreshold(int newThreshold);
}
