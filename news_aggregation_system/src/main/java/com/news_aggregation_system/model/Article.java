package com.news_aggregation_system.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Formula;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long articleId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private String url;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date publishedAt;

    private boolean enabled = true;

    @Formula("(SELECT COUNT(*) FROM article_reactions ar WHERE ar.article_id = article_id AND ar.reaction_type = 'LIKE')")
    private int likeCount;

    @Formula("(SELECT COUNT(*) FROM article_reactions ar WHERE ar.article_id = article_id AND ar.reaction_type = 'DISLIKE')")
    private int dislikeCount;

    @Formula("(SELECT COUNT(*) FROM article_reports ar WHERE ar.article_id = article_id)")
    private int reportCount;

    @ManyToMany
    @JoinTable(name = "article_categories", joinColumns = @JoinColumn(name = "article_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ArticleReaction> reactions = new HashSet<>();

    public Article() {
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public Set<ArticleReaction> getReactions() {
        return reactions;
    }

    public void setReactions(Set<ArticleReaction> reactions) {
        this.reactions = reactions;
    }

    public int getReportCount() {
        return reportCount;
    }

    public void setReportCount(int reportCount) {
        this.reportCount = reportCount;
    }
}
