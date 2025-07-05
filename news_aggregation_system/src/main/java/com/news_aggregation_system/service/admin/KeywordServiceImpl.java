package com.news_aggregation_system.service.admin;

import com.news_aggregation_system.dto.KeywordDTO;
import com.news_aggregation_system.exception.NotFoundException;
import com.news_aggregation_system.mapper.KeywordMapper;
import com.news_aggregation_system.model.Category;
import com.news_aggregation_system.model.Keyword;
import com.news_aggregation_system.repository.CategoryRepository;
import com.news_aggregation_system.repository.KeywordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class KeywordServiceImpl implements KeywordService {

    private final KeywordRepository keywordRepository;
    private final CategoryRepository categoryRepository;


    public KeywordServiceImpl(KeywordRepository keywordRepository, CategoryRepository categoryRepository) {
        this.keywordRepository = keywordRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public KeywordDTO creteKeyword(KeywordDTO keywordDTO) {
        Keyword keyword = keywordRepository.save(KeywordMapper.toEntity(keywordDTO));
        return KeywordMapper.toDto(keyword);
    }

    @Override
    public void deleteKeywordByCategoryIdAndKeywordId(Long categoryId, Long keywordId) {
        int deleted = keywordRepository.deleteKeywordByCategoryCategoryIdAndKeywordId(categoryId, keywordId);
        if (deleted < 1) {
            throw new NotFoundException("Keyword not found so unable to delete");
        }
    }

    @Override
    public void deleteKeywordByCategoryIdAndKeywordName(Long categoryId, String keywordName) {
        int deleted = keywordRepository.deleteKeywordByCategoryCategoryIdAndNameContainingIgnoreCase(categoryId, keywordName);
        if (deleted < 1) {
            throw new NotFoundException("Keyword not found so unable to delete");
        }
    }


    @Override
    public List<KeywordDTO> getAllKeywordsByCategory(Long categoryId) {
        return keywordRepository.getKeywordsByCategoryCategoryId(categoryId).stream().map(KeywordMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void updateKeywordStatus(Long categoryId, Long keywordId, boolean enabled) {
        keywordRepository.updateEnabledByCategoryCategoryIdAndKeywordId(enabled, categoryId, keywordId);
    }

    @Override
    public void updateKeywordStatus(Long categoryId, String keywordName, boolean enabled) {
        int updated = keywordRepository.updateEnabledByCategoryCategoryIdAndNameContainingIgnoreCase(enabled, categoryId, keywordName);
        if (updated < 1) {
            throw new NotFoundException("Keyword not found so unable to update");
        }
    }


    @Override
    public void addKeywordsToCategory(Long categoryId, List<String> keywords) {
        boolean isCategoryExist = categoryRepository.existsById(categoryId);

        if (!isCategoryExist) {
            throw new NotFoundException("Category not found.");
        }
        List<Keyword> keywordList = new ArrayList<>();
        for (String keyword : keywords) {
            String word = keyword.trim().toLowerCase();
            if (word.isBlank()) continue;
            if (keywordRepository.existsByCategoryCategoryIdAndNameContainsIgnoreCase(categoryId, word)) {
                continue;
            } else {
                Keyword keywordEntity = new Keyword();
                Category category = new Category();
                category.setCategoryId(categoryId);

                keywordEntity.setCategory(category);
                keywordEntity.setName(word);
                keywordList.add(keywordEntity);
            }
            keywordRepository.saveAll(keywordList);

        }
    }
}
