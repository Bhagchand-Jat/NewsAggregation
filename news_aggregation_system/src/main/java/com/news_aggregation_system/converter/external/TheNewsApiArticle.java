package com.news_aggregation_system.converter.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TheNewsApiArticle {
    public String title;
    public String description;
    public String snippet;
    public String url;
    public String published_at;
    public String source;
    public List<String> categories;
}
