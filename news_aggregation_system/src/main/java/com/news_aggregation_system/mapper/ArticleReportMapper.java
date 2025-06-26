package com.news_aggregation_system.mapper;

import com.news_aggregation_system.dto.ArticleReportDTO;
import com.news_aggregation_system.model.Article;
import com.news_aggregation_system.model.ArticleReport;
import com.news_aggregation_system.model.User;

public class ArticleReportMapper {
    public static ArticleReport toEntity(ArticleReportDTO articleReportDTO) {
        ArticleReport articleReport = new ArticleReport();
        articleReport.setId(articleReportDTO.getId());
        User user = new User();
        user.setUserId(articleReportDTO.getUserId());
        articleReport.setReportedBy(user);
        Article article = new Article();
        article.setArticleId(articleReportDTO.getArticleId());
        articleReport.setArticle(article);
        articleReport.setReportedAt(articleReportDTO.getReportedAt());
        return articleReport;
    }

    public static ArticleReportDTO toDTO(ArticleReport articleReport) {
        ArticleReportDTO articleReportDTO = new ArticleReportDTO();
        articleReportDTO.setId(articleReport.getId());
        articleReportDTO.setArticleId(articleReport.getArticle().getArticleId());
        articleReportDTO.setArticleId(articleReport.getArticle().getArticleId());
        articleReportDTO.setUserId(articleReport.getReportedBy().getUserId());
        articleReportDTO.setReportedAt(articleReport.getReportedAt());
        return articleReportDTO;
    }
}
