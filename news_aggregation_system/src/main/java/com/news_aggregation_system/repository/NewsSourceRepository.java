package com.news_aggregation_system.repository;

import com.news_aggregation_system.model.NewsSource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsSourceRepository extends JpaRepository<NewsSource, Long> {

}
