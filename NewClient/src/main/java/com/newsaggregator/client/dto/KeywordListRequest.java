package com.newsaggregator.client.dto;


import java.util.List;

public class KeywordListRequest {

    List<String> keywords;

    public KeywordListRequest(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
