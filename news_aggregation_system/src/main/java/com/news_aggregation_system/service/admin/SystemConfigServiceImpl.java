package com.news_aggregation_system.service.admin;

import com.news_aggregation_system.exception.NotFoundException;
import com.news_aggregation_system.model.Article;
import com.news_aggregation_system.model.SystemConfig;
import com.news_aggregation_system.repository.ArticleRepository;
import com.news_aggregation_system.repository.SystemConfigRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SystemConfigServiceImpl implements SystemConfigService {
    private final SystemConfigRepository systemConfigRepository;
    private final ArticleRepository articleRepository;

    public SystemConfigServiceImpl(SystemConfigRepository systemConfigRepository, ArticleRepository articleRepository) {
        this.systemConfigRepository = systemConfigRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public int getCurrentReportThreshold() {
        Optional<SystemConfig> config = systemConfigRepository.findTopByOrderByIdDesc();
        return config.map(SystemConfig::getReportThreshold).orElse(5);
    }

    @Transactional
    @Override
    public void updateThreshold(int newThreshold) {

        if (newThreshold < 1)
            throw new IllegalArgumentException("Threshold must be ≥ 1");

        List<SystemConfig> systemConfigs = systemConfigRepository.findAll();
        if (!systemConfigs.isEmpty()) {
            int updated = systemConfigRepository.updateReportThresholdById(newThreshold, systemConfigs.getFirst().getId());
            if (updated < 1) {
                throw new NotFoundException("News Threshold Not Found");
            }
        } else {
            SystemConfig systemConfig = new SystemConfig();
            systemConfig.setReportThreshold(newThreshold);
            systemConfigRepository.save(systemConfig);
        }

        List<Article> articles = articleRepository.findMaxThresholdAndEnabledArticles(newThreshold);
        if (!articles.isEmpty()) {
            articles.forEach(article -> article.setEnabled(false));
            articleRepository.saveAll(articles);
        }

    }
}

