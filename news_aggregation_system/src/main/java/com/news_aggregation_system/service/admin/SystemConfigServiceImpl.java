package com.news_aggregation_system.service.admin;

import com.news_aggregation_system.exception.NotFoundException;
import com.news_aggregation_system.model.SystemConfig;
import com.news_aggregation_system.repository.SystemConfigRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SystemConfigServiceImpl implements SystemConfigService {
    private final SystemConfigRepository systemConfigRepository;

    public SystemConfigServiceImpl(SystemConfigRepository systemConfigRepository) {
        this.systemConfigRepository = systemConfigRepository;
    }

    @Override
    public int getCurrentReportThreshold() {
        Optional<SystemConfig> config = systemConfigRepository.findTopByOrderByIdDesc();
        return config.map(SystemConfig::getReportThreshold).orElse(5);
    }

    @Override
    public void updateThreshold(Long id, int newThreshold) {

        int updated = systemConfigRepository.updateReportThresholdById(newThreshold, id);
        if (updated < 1) {
            throw new NotFoundException("News Threshold", "id: " + id);
        }
    }
}

