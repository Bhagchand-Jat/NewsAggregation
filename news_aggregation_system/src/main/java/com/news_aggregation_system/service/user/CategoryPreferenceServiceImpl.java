package com.news_aggregation_system.service.user;

import com.news_aggregation_system.dto.CategoryStatusDTO;
import com.news_aggregation_system.dto.UserCategoryPreferenceDTO;
import com.news_aggregation_system.dto.UserKeywordPreferenceDTO;
import com.news_aggregation_system.exception.AlreadyExistsException;
import com.news_aggregation_system.exception.NotFoundException;
import com.news_aggregation_system.mapper.UserCategoryPreferenceMapper;
import com.news_aggregation_system.mapper.UserKeywordPreferenceMapper;
import com.news_aggregation_system.model.Category;
import com.news_aggregation_system.model.User;
import com.news_aggregation_system.model.UserCategoryPreference;
import com.news_aggregation_system.model.UserKeywordPreference;
import com.news_aggregation_system.repository.CategoryRepository;
import com.news_aggregation_system.repository.UserCategoryPreferenceRepository;
import com.news_aggregation_system.repository.UserKeywordPreferenceRepository;
import com.news_aggregation_system.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.news_aggregation_system.service.common.Constant.*;

@Service
@Transactional
public class CategoryPreferenceServiceImpl implements CategoryPreferenceService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final UserKeywordPreferenceRepository userKeywordPreferenceRepository;
    private final UserCategoryPreferenceRepository userCategoryPreferenceRepository;


    public CategoryPreferenceServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository, UserKeywordPreferenceRepository userKeywordPreferenceRepository,
                                         UserCategoryPreferenceRepository userCategoryPreferenceRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.userKeywordPreferenceRepository = userKeywordPreferenceRepository;
        this.userCategoryPreferenceRepository = userCategoryPreferenceRepository;
    }

    @Transactional
    @Override
    public void enableCategoryForUser(Long userId, Long categoryId) {

        userCategoryPreferenceRepository.findByUserUserIdAndCategoryCategoryId(userId, categoryId).ifPresent(pref -> {
            throw new AlreadyExistsException(
                    CATEGORY_ALREADY_ENABLED);
        });

        createUserCategoryPreference(userId, categoryId);
    }

    @Transactional
    @Override
    public void disableCategoryForUser(Long userId, Long categoryId) {

        int deleted = userCategoryPreferenceRepository.deleteByUserUserIdAndCategoryCategoryId(userId, categoryId);
        if (deleted < 1) {
            throw new NotFoundException(CATEGORY_PREFERENCE_NOT_FOUND + userId + AND_CATEGORY_ID + categoryId);
        }
    }

    @Override
    public List<UserCategoryPreferenceDTO> getAllEnabledCategoriesPreference(Long userId) {
        return userCategoryPreferenceRepository.findByUserUserIdAndEnabledTrue(userId).stream().map(UserCategoryPreferenceMapper::toDto).toList();
    }


    private User fetchUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER, ID + id));
    }

    private Category fetchCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CATEGORY, ID + id));
    }

    private void createUserCategoryPreference(Long userId, Long categoryId) {
        User user = fetchUser(userId);
        Category category = fetchCategory(categoryId);
        UserCategoryPreference preference = new UserCategoryPreference();
        preference.setUser(user);
        preference.setCategory(category);
        preference.setEnabled(true);
        userCategoryPreferenceRepository.save(preference);
    }

    @Override
    public List<CategoryStatusDTO> getEnabledCategoriesStatus(Long userId) {

        List<Category> categories = categoryRepository.findByEnabledTrue();

        Set<Long> enabledPreferenceCategoryIds = userCategoryPreferenceRepository
                .findByUserUserIdAndEnabledTrue(userId)
                .stream()
                .map(preference -> preference.getCategory().getCategoryId())
                .collect(Collectors.toSet());

        return categories.stream()
                .map(category -> new CategoryStatusDTO(
                        category.getCategoryId(),
                        category.getName(),
                        enabledPreferenceCategoryIds.contains(category.getCategoryId())
                ))
                .toList();
    }

    @Transactional
    @Override
    public void addKeywordsToCategory(Long userId, Long categoryId, List<String> words) {
        User user = fetchUser(userId);
        Category category = fetchCategory(categoryId);
        List<UserKeywordPreference> keywordPreferences = new ArrayList<>();
        for (String rawWord : words) {
            String word = rawWord.trim().toLowerCase();
            if (word.isBlank()) continue;
            if (userKeywordPreferenceRepository.existsByUserUserIdAndCategoryCategoryIdAndKeywordIgnoreCase(
                    userId, categoryId, word)) continue;

            UserKeywordPreference userKeywordPreference = new UserKeywordPreference();
            userKeywordPreference.setUser(user);
            userKeywordPreference.setCategory(category);
            userKeywordPreference.setKeyword(word);
            keywordPreferences.add(userKeywordPreference);
        }
        userKeywordPreferenceRepository.saveAll(keywordPreferences);
    }

    @Override
    public Set<String> getEnabledKeywords(Long userId) {
        return userKeywordPreferenceRepository.getEnabledKeywordsByUserId(userId)
                .stream().map(UserKeywordPreference::getKeyword).collect(Collectors.toSet());
    }

    @Override
    public List<String> getEnabledKeywordsForCategory(Long userId, Long categoryId) {
        return userKeywordPreferenceRepository.getEnabledKeywordsByUserIdAndCategoryId(userId, categoryId)
                .stream().map(UserKeywordPreference::getKeyword).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteKeywordFromCategory(Long userId, Long categoryId, String keywordName) {
        int delete = userKeywordPreferenceRepository.deleteUserKeywordPreferenceByKeywordAndUserUserIdAndCategoryCategoryId(keywordName, userId, categoryId);
        if (delete < 1) {
            throw new NotFoundException(KEYWORD_NOT_FOUND + userId + AND_CATEGORY_ID + categoryId);
        }
    }

    @Override
    public List<UserKeywordPreferenceDTO> getAllEnabledKeywordPreferences(Long userId) {
        return userKeywordPreferenceRepository.findActiveByUserId(userId).stream().map(UserKeywordPreferenceMapper::toDTO).toList();
    }
}