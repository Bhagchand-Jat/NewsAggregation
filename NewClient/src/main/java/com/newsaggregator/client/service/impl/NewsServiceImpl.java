package com.newsaggregator.client.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.newsaggregator.client.dto.*;
import com.newsaggregator.client.model.ReactionType;
import com.newsaggregator.client.service.BaseService;
import com.newsaggregator.client.service.NewsService;
import com.newsaggregator.client.util.Constant;
import com.newsaggregator.client.util.UiText;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static com.newsaggregator.client.util.Constant.ARTICLE_ID;
import static com.newsaggregator.client.util.Constant.USER_ID;

public class NewsServiceImpl extends BaseService implements NewsService {

    @Override
    public List<ArticleDTO> fetchHeadlines(Date from, Date to, Long categoryId) {
        String url = Constant.API_NEWS_FILTER;
        ArticleFilterRequestDTO filterRequestDTO = new ArticleFilterRequestDTO(categoryId, to, from);
        return safePostList(url, filterRequestDTO, new TypeReference<>() {
        });
    }

    @Override
    public void saveArticle(Long userId, Long articleId) {
        String url = Constant.API_USERS_SAVE_ARTICLE;
        SavedArticleDTO savedArticleDTO = new SavedArticleDTO(userId, articleId);
        safePost(url, savedArticleDTO, new TypeReference<Void>() {
        });
    }

    @Override
    public boolean deleteSavedArticle(Long userId, Long articleId) {
        String url = Constant.API_USERS_DELETE_SAVED_ARTICLE
                .replace(USER_ID, userId.toString())
                .replace(ARTICLE_ID, articleId.toString());
        return safeDelete(url, UiText.SAVED_ARTICLE_DELETED_SUCCESS);
    }

    @Override
    public List<ArticleDTO> getSavedArticles(Long userId) {
        String url = Constant.API_USERS_SAVED_ARTICLES.replace(USER_ID, userId.toString());
        return safeGetList(url, new TypeReference<>() {
        });
    }

    @Override
    public List<ArticleDTO> searchArticles(String query) {

        String url = Constant.API_NEWS_SEARCH + "?keyword=" + query;
        return safeGetList(url, new TypeReference<>() {
        });
    }

    @Override
    public List<ArticleDTO> allNewsArticles() {
        return safeGetList(Constant.API_NEWS, new TypeReference<>() {
        });
    }

    @Override
    public List<ArticleDTO> todayNewsArticles() {
        LocalDate date = LocalDate.now();
        String url = Constant.API_NEWS_DATE + "?date=" + date.format(Constant.isoDateFormatter);
        return safeGetList(url, new TypeReference<>() {
        });
    }

    @Override
    public void reportArticle(Long userId, Long articleId, String reason) {
        String url = Constant.API_USERS_REPORT_ARTICLE;
        ArticleReportDTO articleReportDTO = new ArticleReportDTO(articleId, userId, reason);

        safePost(url, articleReportDTO, new TypeReference<Void>() {
        });
    }

    @Override
    public void likeArticle(Long articleId, Long userId) {
        ArticleReactionDTO body = new ArticleReactionDTO(articleId, userId, ReactionType.LIKE);
        safePost(Constant.API_REACTIONS_LIKE, body, new TypeReference<ArticleReactionDTO>() {
        });
    }

    @Override
    public void disLikeArticle(Long articleId, Long userId) {
        ArticleReactionDTO body = new ArticleReactionDTO(articleId, userId, ReactionType.DISLIKE);
        safePost(Constant.API_REACTIONS_DISLIKE, body, new TypeReference<ArticleReactionDTO>() {
        });
    }

    @Override
    public List<CategoryDTO> getCategories() {
        return safeGetList(Constant.API_USERS_CATEGORIES_ENABLED, new TypeReference<>() {
        });
    }

    @Override
    public List<ArticleReportDTO> getUserReports(Long userId) {
        String url = Constant.API_USERS_REPORTS.replace("{userId}", userId.toString());
        return safeGetList(url, new TypeReference<>() {
        });
    }
}
