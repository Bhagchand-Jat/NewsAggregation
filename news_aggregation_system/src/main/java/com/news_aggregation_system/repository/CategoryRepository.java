package com.news_aggregation_system.repository;

import com.news_aggregation_system.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

    Optional<Category> findByNameIgnoreCase(String name);

    List<Category> findByEnabledTrue();

    @Modifying
    @Transactional
    @Query("update Category  c set  c.enabled= :enabled where c.categoryId=:categoryId")
    int updateEnabledByCategoryId(@Param("enabled") boolean isEnabled, @Param("categoryId") Long categoryId);
}
