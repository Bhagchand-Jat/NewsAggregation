package com.news_aggregation_system.service.admin;

import com.news_aggregation_system.exception.NotFoundException;
import com.news_aggregation_system.model.Article;
import com.news_aggregation_system.model.SystemConfig;
import com.news_aggregation_system.repository.ArticleRepository;
import com.news_aggregation_system.repository.SystemConfigRepository;
import com.news_aggregation_system.service.common.Constant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.news_aggregation_system.service.common.Constant.NEWS_THRESHOLD_NOT_FOUND;
import static com.news_aggregation_system.service.common.Constant.THRESHOLD_MUST_GREATER;

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
        return config.map(SystemConfig::getReportThreshold).orElse(Constant.THRESHOLD_VALUE);
    }

    @Transactional
    @Override
    public void updateThreshold(int newThreshold) {

        if (newThreshold < 1)
            throw new IllegalArgumentException(THRESHOLD_MUST_GREATER);

        List<SystemConfig> systemConfigs = systemConfigRepository.findAll();
        if (!systemConfigs.isEmpty()) {
            int updated = systemConfigRepository.updateReportThresholdById(newThreshold, systemConfigs.getFirst().getId());
            if (updated < 1) {
                throw new NotFoundException(NEWS_THRESHOLD_NOT_FOUND);
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

