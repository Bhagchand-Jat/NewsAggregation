package com.news_aggregation_system.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ArticleFilterRequestDTO {
    private String keyword;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date date;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date from;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date to;

    private Long categoryId;

    // Getters and Setters
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
