package com.news_aggregation_system.service.user;

import com.news_aggregation_system.dto.KeywordDTO;
import com.news_aggregation_system.exception.NotFoundException;
import com.news_aggregation_system.mapper.KeywordMapper;
import com.news_aggregation_system.model.Keyword;
import com.news_aggregation_system.model.User;
import com.news_aggregation_system.repository.KeywordRepository;
import com.news_aggregation_system.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class KeywordServiceImpl implements KeywordService {

    private final KeywordRepository keywordRepository;
    private final UserRepository userRepository;


    public KeywordServiceImpl(KeywordRepository keywordRepository, UserRepository userRepository) {
        this.keywordRepository = keywordRepository;
        this.userRepository = userRepository;
    }


    @Override
    public KeywordDTO creteKeyword(Long userId, KeywordDTO keywordDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User", "id: " + userId));
        Keyword keyword = KeywordMapper.toEntity(keywordDTO);
        keyword.setUser(user);
        return KeywordMapper.toDto(keywordRepository.save(keyword));
    }


    @Override
    public void deleteKeywordByIdAndUserId(Long keywordId, Long userId) {

        int deleted = keywordRepository.deleteKeywordByKeywordIdAndUserUserId(keywordId, userId);
        if (deleted < 1) {
            throw new NotFoundException("Keyword", "userId" + userId);
        }

    }


    @Override
    public List<KeywordDTO> getAllKeywordsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User", "id: " + userId));
        return keywordRepository.findAllByUser(user).stream().map(KeywordMapper::toDto).toList();
    }


    @Override
    public void updateKeywordStatus(Long keywordId, boolean enabled) {
        int updated = keywordRepository.updateEnabledByKeywordId(keywordId, enabled);
        if (updated < 1) {
            throw new NotFoundException("Keyword", "id: " + keywordId);
        }
    }


}
