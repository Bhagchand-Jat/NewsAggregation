package com.news_aggregation_system.service.admin;

import com.news_aggregation_system.dto.NewsSourceDTO;
import com.news_aggregation_system.exception.NotFoundException;
import com.news_aggregation_system.mapper.NewsSourceMapper;
import com.news_aggregation_system.model.NewsSource;
import com.news_aggregation_system.repository.NewsSourceRepository;
import com.news_aggregation_system.service.common.Constant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.news_aggregation_system.service.common.Constant.NEWS_SOURCE;

@Service
public class NewsSourceServiceImpl implements NewsSourceService {

    private final NewsSourceRepository newsSourceRepository;

    public NewsSourceServiceImpl(NewsSourceRepository newsSourceRepository) {
        this.newsSourceRepository = newsSourceRepository;
    }

    @Transactional
    @Override
    public NewsSourceDTO create(NewsSourceDTO newsSourceDTO) {
        NewsSource entity = NewsSourceMapper.toEntity(newsSourceDTO);
        NewsSource saved = newsSourceRepository.save(entity);
        return NewsSourceMapper.toDto(saved);
    }

    @Transactional
    @Override
    public NewsSourceDTO update(Long id, NewsSourceDTO newsSourceDTO) {
        NewsSource existing = getNewsSourceOrThrow(id);
        existing.setSourceApiKey(newsSourceDTO.getSourceApiKey());
        existing.setEnabled(newsSourceDTO.isEnabled());
        return NewsSourceMapper.toDto(newsSourceRepository.save(existing));
    }

    @Override
    public NewsSourceDTO getById(Long id) {
        return NewsSourceMapper.toDto(getNewsSourceOrThrow(id));
    }

    @Override
    public List<NewsSourceDTO> getAll() {
        return newsSourceRepository.findAll()
                .stream()
                .map(NewsSourceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void delete(Long id) {
        NewsSource source = getNewsSourceOrThrow(id);
        newsSourceRepository.delete(source);
    }

    private NewsSource getNewsSourceOrThrow(Long id) {
        return newsSourceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NEWS_SOURCE, Constant.ID + id));
    }

    @Transactional
    @Override
    public List<NewsSourceDTO> getAllByEnabledAndUpdateLastModified() {
        List<NewsSource> newsSources = newsSourceRepository.findByEnabledTrue();
        for (NewsSource newsSource : newsSources) {
            newsSourceRepository.updateLastAccessed(newsSource.getSourceId(), LocalDateTime.now());
        }
        return newsSources
                .stream()
                .map(NewsSourceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void updateSourceApiKeyById(Long sourceId, String sourceApiKey) {
        int updated = newsSourceRepository.updateSourceApiKey(sourceId, sourceApiKey);
        if (updated < 1) {
            throw new NotFoundException(NEWS_SOURCE, Constant.ID + sourceId);
        }

    }
}