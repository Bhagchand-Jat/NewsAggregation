package com.news_aggregation_system.service.user;

import com.news_aggregation_system.dto.CategoryStatusDTO;
import com.news_aggregation_system.dto.UserCategoryPreferenceDTO;
import com.news_aggregation_system.exception.AlreadyExistsException;
import com.news_aggregation_system.exception.NotFoundException;
import com.news_aggregation_system.mapper.UserCategoryPreferenceMapper;
import com.news_aggregation_system.model.Category;
import com.news_aggregation_system.model.User;
import com.news_aggregation_system.model.UserCategoryPreference;
import com.news_aggregation_system.repository.CategoryRepository;
import com.news_aggregation_system.repository.UserCategoryPreferenceRepository;
import com.news_aggregation_system.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryPreferenceServiceImpl implements CategoryPreferenceService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final UserCategoryPreferenceRepository userCategoryPreferenceRepository;


    public CategoryPreferenceServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository,
                                         UserCategoryPreferenceRepository userCategoryPreferenceRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.userCategoryPreferenceRepository = userCategoryPreferenceRepository;
    }

    @Override
    public UserCategoryPreferenceDTO createPreference(Long userId, Long categoryId, boolean enabled) {

        userCategoryPreferenceRepository.findByUserUserIdAndCategoryCategoryId(userId, categoryId).ifPresent(pref -> {
            throw new AlreadyExistsException(
                    "CategoryPreference", "userId=" + userId + ", categoryId=" + categoryId);
        });

        return UserCategoryPreferenceMapper.toDto(createUserCategoryPreference(userId, categoryId));
    }

    @Override
    public void deletePreference(Long userId, Long categoryId) {

        int deleted = userCategoryPreferenceRepository.deleteByUserUserIdAndCategoryCategoryId(userId, categoryId);
        if (deleted < 1) {
            throw new NotFoundException("CategoryPreference Not found with userId: " + userId + " and categoryId: " + categoryId);
        }
    }

    @Override
    public void enableCategoryForUser(Long userId, Long categoryId) {
        int updated = userCategoryPreferenceRepository.updateEnabledTrueByUserUserIdAndCategoryCategoryId(userId, categoryId);
        if (updated < 1) {
            createUserCategoryPreference(userId, categoryId);
        }


    }

    @Override
    public void disableCategoryForUser(Long userId, Long categoryId) {
        int deleted = userCategoryPreferenceRepository.deleteByUserUserIdAndCategoryCategoryId(userId, categoryId);
        if (deleted < 1) {
            throw new NotFoundException("UserCategoryPreference Not found with userId: " + userId + " and categoryId: " + categoryId);
        }
    }

    @Override
    public List<Category> getEnabledCategories(Long userId) {

        return userCategoryPreferenceRepository.findByUserUserIdAndEnabledTrue(userId)
                .stream()
                .map(UserCategoryPreference::getCategory)
                .toList();
    }


    private User fetchUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User", "id=" + id));
    }

    private Category fetchCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category", "id=" + id));
    }

    private UserCategoryPreference createUserCategoryPreference(Long userId, Long categoryId) {
        User user = fetchUser(userId);
        Category category = fetchCategory(categoryId);
        UserCategoryPreference preference = new UserCategoryPreference();
        preference.setUser(user);
        preference.setCategory(category);
        preference.setEnabled(true);
        return userCategoryPreferenceRepository.save(preference);
    }

    @Override
    public List<CategoryStatusDTO> getEnabledCategoriesStatus(Long userId) {
        Set<Long> enabledCatIds = userCategoryPreferenceRepository.findByUserUserIdAndEnabledTrue(
                userId
        ).stream().map(p -> p.getCategory().getCategoryId()).collect(Collectors.toSet());

        return categoryRepository.findByEnabledTrue().stream()
                .map(category -> new CategoryStatusDTO(
                        category.getCategoryId(),
                        category.getName(),
                        enabledCatIds.contains(category.getCategoryId())))
                .toList();
    }
}
