package com.news_aggregation_system.model;

import jakarta.persistence.*;

@Entity
@Table(name = "system_config")
public class SystemConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_threshold", nullable = false)
    private int reportThreshold;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getReportThreshold() {
        return reportThreshold;
    }

    public void setReportThreshold(int reportThreshold) {
        this.reportThreshold = reportThreshold;
    }
}

