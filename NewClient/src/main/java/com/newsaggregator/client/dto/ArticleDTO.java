package com.newsaggregator.client.dto;

import com.newsaggregator.client.model.Category;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class ArticleDTO {

    private Long articleId;

    private String title;

    private String content;

    private String description;

    private String source;


    private String url;

    private Date publishedAt;
    private boolean enabled;
    private Set<Category> categories = new HashSet<>();


    public ArticleDTO() {
    }

    public Long getArticleId() {
        return articleId;
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

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "ArticleDTO{" + "articleId=" + articleId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", description='" + description + '\'' +
                ", source='" + source + '\'' +
                ", url='" + url + '\'' +
                ", publishedAt=" + publishedAt +
                ", enabled=" + enabled +
                ", categories=" + categories +
                '}';
    }
}
