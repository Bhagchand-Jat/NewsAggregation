package com.newsaggregator.client.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.newsaggregator.client.dto.*;
import com.newsaggregator.client.model.ReactionType;
import com.newsaggregator.client.service.BaseService;
import com.newsaggregator.client.service.NewsService;
import com.newsaggregator.client.util.Constant;
import com.newsaggregator.client.util.UiText;

import java.util.Date;
import java.util.List;

import static com.newsaggregator.client.util.Constant.*;

public class NewsServiceImpl extends BaseService implements NewsService {

    @Override
    public List<ArticleDTO> fetchHeadlines(Date from, Date to, Long categoryId,Long userId) {
        ArticleFilterRequestDTO filterRequestDTO = new ArticleFilterRequestDTO(categoryId, to, from);
        return safePostList(Constant.API_NEWS_FILTER.replace(USER_ID,userId.toString()), filterRequestDTO, new TypeReference<>() {
        });
    }

    @Override
    public void saveArticle(Long userId, Long articleId) {
        SavedArticleDTO savedArticleDTO = new SavedArticleDTO(userId, articleId);
        safePost(Constant.API_USERS_SAVE_ARTICLE, savedArticleDTO, new TypeReference<Void>() {
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
    public List<ArticleDTO> searchArticles(String query,Long userId) {

        ArticleFilterRequestDTO filterRequestDTO = new ArticleFilterRequestDTO(query);
        return safePostList(API_NEWS_FILTER.replace(USER_ID,userId.toString()), filterRequestDTO, new TypeReference<>() {
        });
    }

    @Override
    public List<ArticleDTO> allNewsArticles() {
        return safeGetList(Constant.API_NEWS, new TypeReference<>() {
        });
    }

    @Override
    public List<ArticleDTO> todayNewsArticles(Long userId) {

        Date today = new Date(System.currentTimeMillis());
        ArticleFilterRequestDTO filterRequestDTO = new ArticleFilterRequestDTO(today);
        return safePostList(Constant.API_NEWS_FILTER.replace(USER_ID,userId.toString()), filterRequestDTO, new TypeReference<>() {
        });
    }

    @Override
    public void reportArticle(Long userId, Long articleId, String reason) {
        ArticleReportDTO articleReportDTO = new ArticleReportDTO(articleId, userId, reason);

        safePost(Constant.API_USERS_REPORT_ARTICLE, articleReportDTO, new TypeReference<Void>() {
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
        String url = Constant.API_USERS_REPORTS.replace(USER_ID, userId.toString());
        return safeGetList(url, new TypeReference<>() {
        });
    }

    @Override
    public void markArticleAsRead(Long userId, Long articleId) {
        String url = Constant.API_USERS_MARK_ARTICLE_AS_READ
                .replace(USER_ID, userId.toString())
                .replace(ARTICLE_ID, articleId.toString());
        safePost(url, null, new TypeReference<>() {
        });
    }

    @Override
    public List<ArticleReadHistoryDTO> getArticlesReadHistory(Long userId) {
        String url = Constant.API_USERS_ARTICLES_READ_HISTORY
                .replace(USER_ID, userId.toString());

        return safeGetList(url, new TypeReference<>() {
        });
    }


}
