package com.news_aggregation_system.service.admin;

import com.news_aggregation_system.dto.NewsSourceDTO;
import com.news_aggregation_system.exception.NotFoundException;
import com.news_aggregation_system.mapper.NewsSourceMapper;
import com.news_aggregation_system.model.NewsSource;
import com.news_aggregation_system.repository.NewsSourceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsSourceServiceImpl implements NewsSourceService {

    private final NewsSourceRepository newsSourceRepository;

    public NewsSourceServiceImpl(NewsSourceRepository newsSourceRepository) {
        this.newsSourceRepository = newsSourceRepository;
    }

    @Override
    public NewsSourceDTO create(NewsSourceDTO newsSourceDTO) {
        NewsSource entity = NewsSourceMapper.toEntity(newsSourceDTO);
        NewsSource saved = newsSourceRepository.save(entity);
        return NewsSourceMapper.toDto(saved);
    }

    @Override
    public NewsSourceDTO update(Long id, NewsSourceDTO newsSourceDTO) {
        NewsSource existing = getNewsSourceOrThrow(id);
        existing.setSourceApiKey(newsSourceDTO.getSourceApiKey());
        existing.setSourceUrl(newsSourceDTO.getSourceUrl());
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

    @Override
    public void delete(Long id) {
        NewsSource source = getNewsSourceOrThrow(id);
        newsSourceRepository.delete(source);
    }

    private NewsSource getNewsSourceOrThrow(Long id) {
        return newsSourceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("NewsSource", "id: " + id));
    }
}