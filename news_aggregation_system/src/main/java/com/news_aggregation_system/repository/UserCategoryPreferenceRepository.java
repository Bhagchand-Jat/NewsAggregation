package com.news_aggregation_system.repository;

import com.news_aggregation_system.model.Category;
import com.news_aggregation_system.model.User;
import com.news_aggregation_system.model.UserCategoryPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCategoryPreferenceRepository extends JpaRepository<UserCategoryPreference, Long> {

    List<UserCategoryPreference> findByUserAndEnabledTrue(User user);

    Optional<UserCategoryPreference> findByUserAndCategory(User user, Category category);

}
