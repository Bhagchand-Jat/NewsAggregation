package com.news_aggregation_system.service.user;

import com.news_aggregation_system.dto.KeywordDTO;
import com.news_aggregation_system.exception.NotFoundException;
import com.news_aggregation_system.mapper.KeywordMapper;
import com.news_aggregation_system.model.Keyword;
import com.news_aggregation_system.model.User;
import com.news_aggregation_system.repository.KeywordRepository;
import com.news_aggregation_system.repository.UserRepository;

import java.util.List;

import org.springframework.stereotype.Service;

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
        Keyword keyword = keywordRepository.findByKeywordIdAndUser_UserId(keywordId, userId)
                .orElseThrow(() -> new NotFoundException("Keyword", "userId" + userId));

        keywordRepository.delete(keyword);
        keywordRepository.deleteById(keywordId);
    }


    @Override
    public List<KeywordDTO> getAllKeywordsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User", "id: " + userId));
        return keywordRepository.findAllByUser(user).stream().map(KeywordMapper::toDto).toList();
    }


    @Override
    public KeywordDTO updateKeyword(KeywordDTO keywordDTO) {
        Keyword keyword = keywordRepository.findById(keywordDTO.getKeywordId()).orElseThrow(() -> new NotFoundException("Keyword", "id: " + keywordDTO.getKeywordId()));
        keyword.setName(keywordDTO.getName());
        return KeywordMapper.toDto(keywordRepository.save(keyword));
    }


}
