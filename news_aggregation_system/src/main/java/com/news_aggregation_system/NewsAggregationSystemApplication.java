package com.news_aggregation_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NewsAggregationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsAggregationSystemApplication.class, args);
    }

}
