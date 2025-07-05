package com.news_aggregation_system.service.admin;

import com.news_aggregation_system.dto.KeywordDTO;

import java.util.List;

public interface KeywordService {

    KeywordDTO creteKeyword(KeywordDTO keywordDTO);

    void deleteKeywordByCategoryIdAndKeywordName(Long categoryId, String keywordName);

    List<KeywordDTO> getAllKeywordsByCategory(Long categoryId);

    void updateKeywordStatus(Long categoryId, String keywordName, boolean enabled);

    void addKeywordsToCategory(Long categoryId, List<String> keywords);

    List<KeywordDTO> findByEnabledFalse();
}
