package com.news_aggregation_system.model;

import jakarta.persistence.*;

@Entity
@Table(name = "keywords",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"category_id", "name"}))
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keywordId;

    @Column(nullable = false)
    private String name;

    private boolean enabled = true;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    public Keyword() {
    }

    public Long getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(Long keywordId) {
        this.keywordId = keywordId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

