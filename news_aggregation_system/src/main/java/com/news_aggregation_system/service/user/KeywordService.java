package com.news_aggregation_system.service.user;

import com.news_aggregation_system.dto.KeywordDTO;

import java.util.List;

public interface KeywordService {

    KeywordDTO creteKeyword(Long userId, KeywordDTO keywordDTO);

    void deleteKeywordByIdAndUserId(Long keywordId, Long userId);

    List<KeywordDTO> getAllKeywordsByUserId(Long userId);

    void updateKeywordStatus(Long keywordId, boolean enabled);

}
