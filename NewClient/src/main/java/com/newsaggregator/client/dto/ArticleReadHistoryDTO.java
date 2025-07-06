package com.newsaggregator.client.dto;

import java.util.Date;

public class ArticleReadHistoryDTO {
    private String articleTitle;
    private Date readAt;

    public ArticleReadHistoryDTO() {
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public Date getReadAt() {
        return readAt;
    }

    public void setReadAt(Date readAt) {
        this.readAt = readAt;
    }
}
