package com.news_aggregation_system.service.admin;

import com.news_aggregation_system.dto.KeywordDTO;
import com.news_aggregation_system.mapper.KeywordMapper;
import com.news_aggregation_system.model.Keyword;
import com.news_aggregation_system.repository.KeywordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class KeywordServiceImpl implements KeywordService {

    private final KeywordRepository keywordRepository;


    public KeywordServiceImpl(KeywordRepository keywordRepository) {
        this.keywordRepository = keywordRepository;
    }


    @Override
    public KeywordDTO creteKeyword(KeywordDTO keywordDTO) {
        Keyword keyword = keywordRepository.save(KeywordMapper.toEntity(keywordDTO));
        return KeywordMapper.toDto(keyword);
    }

    @Override
    public void deleteKeywordById(Long keywordId) {
        keywordRepository.deleteById(keywordId);
    }

    @Override
    public List<KeywordDTO> getAllKeywordsByCategory(Long categoryId) {
        return keywordRepository.getKeywordsByCategoryCategoryId(categoryId).stream().map(KeywordMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void updateKeywordStatus(Long keywordId, boolean enabled) {
        keywordRepository.updateEnabledByKeywordId(keywordId, enabled);
    }
}
