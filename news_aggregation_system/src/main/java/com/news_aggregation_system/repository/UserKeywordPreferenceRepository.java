package com.news_aggregation_system.repository;

import com.news_aggregation_system.model.UserKeywordPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserKeywordPreferenceRepository
        extends JpaRepository<UserKeywordPreference, Long> {

    boolean existsByUserUserIdAndCategoryCategoryIdAndKeywordIgnoreCase(
            Long userId, Long categoryId, String keyword);

    @Query("""
                select nk from UserKeywordPreference nk
                join UserCategoryPreference p
                  on nk.user = p.user and nk.category = p.category
                where p.enabled = true and nk.user.userId = :userId
            """)
    List<UserKeywordPreference> getEnabledKeywordsByUserId(Long userId);

    int deleteUserKeywordPreferenceByKeywordAndUserUserIdAndCategoryCategoryId(String keywordName, Long userId, Long categoryId);

    @Query("""
                select kp from UserKeywordPreference kp
                join UserCategoryPreference p
                  on kp.user = p.user and kp.category = p.category and kp.category.enabled=true
                where p.enabled = true and kp.user.userId = :userId and kp.category.categoryId = :categoryId
            """)
    List<UserKeywordPreference> getEnabledKeywordsByUserIdAndCategoryId(Long userId, Long categoryId);
}

