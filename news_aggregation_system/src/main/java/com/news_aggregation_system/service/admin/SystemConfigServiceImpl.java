package com.news_aggregation_system.service.admin;
import com.news_aggregation_system.model.SystemConfig;
import com.news_aggregation_system.repository.SystemConfigRepository;
import org.springframework.stereotype.Service;

@Service
public class SystemConfigServiceImpl implements  SystemConfigService{
    private final SystemConfigRepository systemConfigRepository;

    public SystemConfigServiceImpl(SystemConfigRepository systemConfigRepository) {
        this.systemConfigRepository = systemConfigRepository;
    }

    @Override
    public int getCurrentReportThreshold() {
        SystemConfig config = systemConfigRepository.findTopByOrderByIdDesc();
        return config != null ? config.getReportThreshold() : 5;
    }

    @Override
    public SystemConfig updateThreshold(int newThreshold) {
        SystemConfig config = systemConfigRepository.findTopByOrderByIdDesc();
        if (config == null) {
            config = new SystemConfig();
        }
        config.setReportThreshold(newThreshold);
        return systemConfigRepository.save(config);
    }
}

