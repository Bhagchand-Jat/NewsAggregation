package com.newsaggregator.client.dto;

import java.util.Date;

public class ArticleFilterRequestDTO {
    private String keyword;

    private Date date;

    private Date from;

    private Date to;

    private Long categoryId;

    public ArticleFilterRequestDTO(String keyword) {
        this.keyword = keyword;
    }

    public ArticleFilterRequestDTO(Date date) {
        this.date = date;
    }

    public ArticleFilterRequestDTO(Date from, Date to) {
        this.from = from;
        this.to = to;
    }

    public ArticleFilterRequestDTO(Long categoryId, Date to, Date from) {
        this.categoryId = categoryId;
        this.to = to;
        this.from = from;
    }

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
