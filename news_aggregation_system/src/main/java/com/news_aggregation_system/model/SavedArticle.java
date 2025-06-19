package com.news_aggregation_system.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "saved_articles")
public class SavedArticle {

    @EmbeddedId
    private SavedArticleId id = new SavedArticleId();

    @ManyToOne
    @MapsId("userId")
    private User user;

    @ManyToOne
    @MapsId("articleId")
    private Article article;

    private LocalDateTime savedAt;

    public SavedArticleId getId() {
        return id;
    }

    public void setId(SavedArticleId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public LocalDateTime getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(LocalDateTime savedAt) {
        this.savedAt = savedAt;
    }
}

