package com.news_aggregation_system.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "notification_configs")
public class NotificationConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Column(nullable = false)
    private boolean keywordsEnabled = false;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserCategoryPreference> categoryPreferences = new HashSet<>();

    public NotificationConfig() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isKeywordsEnabled() {
        return keywordsEnabled;
    }

    public void setKeywordsEnabled(boolean keywordsEnabled) {
        this.keywordsEnabled = keywordsEnabled;
    }

    public Set<UserCategoryPreference> getCategoryPreferences() {
        return categoryPreferences;
    }

    public void setCategoryPreferences(Set<UserCategoryPreference> categoryPreferences) {
        this.categoryPreferences = categoryPreferences;
    }
}
