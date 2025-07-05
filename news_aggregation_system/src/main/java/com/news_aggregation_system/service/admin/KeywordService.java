package com.news_aggregation_system.service.admin;

import com.news_aggregation_system.dto.KeywordDTO;

import java.util.List;

public interface KeywordService {

    KeywordDTO creteKeyword(KeywordDTO keywordDTO);

    void deleteKeywordById(Long keywordId);

    List<KeywordDTO> getAllKeywordsByCategory(Long categoryId);

    void updateKeywordStatus(Long keywordId, boolean enabled);

}
