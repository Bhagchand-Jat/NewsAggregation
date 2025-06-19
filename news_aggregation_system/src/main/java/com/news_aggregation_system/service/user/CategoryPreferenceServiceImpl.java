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
        User user = fetchUser(userId);
        Category category = fetchCategory(categoryId);

        userCategoryPreferenceRepository.findByUserAndCategory(user, category).ifPresent(pref -> {
            throw new AlreadyExistsException(
                    "CategoryPreference", "userId=" + userId + ", categoryId=" + categoryId);
        });

        return UserCategoryPreferenceMapper.toDto(createUserCategoryPreference(userId, categoryId));
    }

    @Override
    public void deletePreference(Long userId, Long categoryId) {
        User user = fetchUser(userId);
        Category category = fetchCategory(categoryId);

        userCategoryPreferenceRepository.findByUserAndCategory(user, category)
                .ifPresentOrElse(
                        userCategoryPreferenceRepository::delete,
                        () -> {
                            throw new NotFoundException(
                                    "CategoryPreference", "userId=" + userId + ", categoryId=" + categoryId);
                        }
                );
    }

    @Override
    public void enableCategoryForUser(Long userId, Long categoryId) {
        userCategoryPreferenceRepository
                .findByUserAndCategory(fetchUser(userId), fetchCategory(categoryId))
                .orElseGet(() -> createUserCategoryPreference(userId, categoryId));

    }

    @Override
    public void disableCategoryForUser(Long userId, Long categoryId) {
        userCategoryPreferenceRepository.findByUserAndCategory(fetchUser(userId), fetchCategory(categoryId))
                .ifPresent(preference -> {
                    preference.setEnabled(false);
                    userCategoryPreferenceRepository.save(preference);
                });
    }

    @Override
    @Transactional()
    public List<Category> getEnabledCategories(Long userId) {
        User user = fetchUser(userId);
        return userCategoryPreferenceRepository.findByUserAndEnabledTrue(user)
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
    public List<CategoryStatusDTO> getCategoryStatuses(Long userId) {
        Set<Long> enabledCatIds = userCategoryPreferenceRepository.findByUserAndEnabledTrue(
                userRepository.getReferenceById(userId)
        ).stream().map(p -> p.getCategory().getCategoryId()).collect(Collectors.toSet());

        return categoryRepository.findAll().stream()
                .map(cat -> new CategoryStatusDTO(
                        cat.getCategoryId(),
                        cat.getName(),
                        enabledCatIds.contains(cat.getCategoryId())))
                .toList();
    }
}
