package com.news_aggregation_system.converter.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsApiOrgArticle {
    public Source source;
    public String title;
    public String description;
    public String content;
    public String url;
    public String publishedAt;
    public  String author;

    public static class Source {
        public String id;
        public String name;
    }
}