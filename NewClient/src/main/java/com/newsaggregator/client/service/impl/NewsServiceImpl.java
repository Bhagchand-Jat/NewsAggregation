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

import static com.newsaggregator.client.util.Constant.API_NEWS_FILTER;
import static com.newsaggregator.client.util.Constant.ARTICLE_ID;

public class NewsServiceImpl extends BaseService implements NewsService {

    @Override
    public List<ArticleDTO> fetchHeadlines(Date from, Date to, Long categoryId) {
        ArticleFilterRequestDTO filterRequestDTO = new ArticleFilterRequestDTO(categoryId, to, from);
        return safePostList(Constant.API_NEWS_FILTER, filterRequestDTO, new TypeReference<>() {
        });
    }

    @Override
    public void saveArticle(Long articleId) {
        SavedArticleDTO savedArticleDTO = new SavedArticleDTO(articleId);
        safePost(Constant.API_USERS_SAVE_ARTICLE, savedArticleDTO, new TypeReference<Void>() {
        });
    }

    @Override
    public boolean deleteSavedArticle(Long articleId) {
        String url = Constant.API_USERS_DELETE_SAVED_ARTICLE
                .replace(ARTICLE_ID, articleId.toString());
        return safeDelete(url, UiText.SAVED_ARTICLE_DELETED_SUCCESS);
    }

    @Override
    public List<ArticleDTO> getSavedArticles() {
        String url = Constant.API_USERS_SAVED_ARTICLES;
        return safeGetList(url, new TypeReference<>() {
        });
    }

    @Override
    public List<ArticleDTO> searchArticles(String query) {

        ArticleFilterRequestDTO filterRequestDTO = new ArticleFilterRequestDTO(query);
        return safePostList(API_NEWS_FILTER, filterRequestDTO, new TypeReference<>() {
        });
    }

    @Override
    public List<ArticleDTO> allNewsArticles() {
        return safeGetList(Constant.API_NEWS, new TypeReference<>() {
        });
    }

    @Override
    public List<ArticleDTO> todayNewsArticles() {

        Date today = new Date(System.currentTimeMillis());
        ArticleFilterRequestDTO filterRequestDTO = new ArticleFilterRequestDTO(today);
        return safePostList(Constant.API_NEWS_FILTER, filterRequestDTO, new TypeReference<>() {
        });
    }

    @Override
    public void reportArticle(Long articleId, String reason) {
        ArticleReportDTO articleReportDTO = new ArticleReportDTO(articleId, reason);

        safePost(Constant.API_USERS_REPORT_ARTICLE, articleReportDTO, new TypeReference<Void>() {
        });
    }

    @Override
    public void likeArticle(Long articleId) {
        ArticleReactionDTO body = new ArticleReactionDTO(articleId, ReactionType.LIKE);
        safePost(Constant.API_REACTIONS_LIKE, body, new TypeReference<ArticleReactionDTO>() {
        });
    }

    @Override
    public void disLikeArticle(Long articleId) {
        ArticleReactionDTO body = new ArticleReactionDTO(articleId, ReactionType.DISLIKE);
        safePost(Constant.API_REACTIONS_DISLIKE, body, new TypeReference<ArticleReactionDTO>() {
        });
    }

    @Override
    public List<CategoryDTO> getCategories() {
        return safeGetList(Constant.API_USERS_CATEGORIES_ENABLED, new TypeReference<>() {
        });
    }

    @Override
    public List<ArticleReportDTO> getUserReports() {
        return safeGetList(Constant.API_USERS_REPORTS, new TypeReference<>() {
        });
    }

    @Override
    public void markArticleAsRead(Long articleId) {
        String url = Constant.API_USERS_MARK_ARTICLE_AS_READ

                .replace(ARTICLE_ID, articleId.toString());
        safePost(url, null, new TypeReference<>() {
        });
    }

    @Override
    public List<ArticleReadHistoryDTO> getArticlesReadHistory() {

        return safeGetList(Constant.API_USERS_ARTICLES_READ_HISTORY, new TypeReference<>() {
        });
    }


}
