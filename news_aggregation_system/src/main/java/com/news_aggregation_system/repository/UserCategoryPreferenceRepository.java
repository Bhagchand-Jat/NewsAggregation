package com.news_aggregation_system.repository;

import com.news_aggregation_system.model.User;
import com.news_aggregation_system.model.UserCategoryPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCategoryPreferenceRepository extends JpaRepository<UserCategoryPreference, Long> {

    List<UserCategoryPreference> findByUserUserIdAndEnabledTrueAndCategoryEnabledTrue(Long userId);

    int deleteByUserUserIdAndCategoryCategoryId(Long userId, Long categoryId);

    List<UserCategoryPreference> user(User user);

    Optional<UserCategoryPreference> findByUserUserIdAndCategoryCategoryId(Long userId, Long categoryId);
}
