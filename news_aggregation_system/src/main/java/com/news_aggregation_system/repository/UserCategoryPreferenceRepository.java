package com.news_aggregation_system.repository;

import com.news_aggregation_system.model.User;
import com.news_aggregation_system.model.UserCategoryPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserCategoryPreferenceRepository extends JpaRepository<UserCategoryPreference, Long> {

    List<UserCategoryPreference> findByUserUserIdAndEnabledTrueAndCategoryEnabledTrue(Long userId);

    @Modifying
    @Transactional
    @Query("update  UserCategoryPreference  pref set pref.enabled=true where pref.user.userId=:userId and pref.category.categoryId=:categoryId")
    int updateEnabledTrueByUserUserIdAndCategoryCategoryId(@Param("userId") Long userId, @Param("categoryId") Long categoryId);

    int deleteByUserUserIdAndCategoryCategoryId(Long userId, Long categoryId);

    List<UserCategoryPreference> user(User user);

    Optional<UserCategoryPreference> findByUserUserIdAndCategoryCategoryId(Long userId, Long categoryId);
}
