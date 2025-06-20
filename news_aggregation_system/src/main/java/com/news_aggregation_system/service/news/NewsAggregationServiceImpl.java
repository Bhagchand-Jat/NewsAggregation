package com.news_aggregation_system.service.news;

import com.news_aggregation_system.dto.ArticleDTO;
import com.news_aggregation_system.dto.NewsSourceDTO;
import com.news_aggregation_system.exception.AlreadyExistsException;
import com.news_aggregation_system.exception.NotFoundException;
import com.news_aggregation_system.mapper.ArticleMapper;
import com.news_aggregation_system.model.Article;
import com.news_aggregation_system.model.ArticleReport;
import com.news_aggregation_system.model.User;
import com.news_aggregation_system.repository.ArticleReportRepository;
import com.news_aggregation_system.repository.ArticleRepository;

import com.news_aggregation_system.service.admin.NewsSourceService;
import com.news_aggregation_system.service.admin.SystemConfigService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class NewsAggregationServiceImpl implements NewsAggregationService {

    private final ArticleRepository articleRepository;
    private final NewsSourceService newsSourceService;
    private final ExternalNewsFetcherService fetcherService;
    private  final ArticleReportRepository articleReportRepository;
private final SystemConfigService systemConfigService;

    public NewsAggregationServiceImpl(ArticleRepository articleRepository, NewsSourceService newsSourceService, ExternalNewsFetcherService fetcherService, ArticleReportRepository articleReportRepository, SystemConfigService systemConfigService) {
        this.articleRepository = articleRepository;
        this.newsSourceService = newsSourceService;
        this.fetcherService = fetcherService;
        this.articleReportRepository = articleReportRepository;
        this.systemConfigService = systemConfigService;
    }

    @Override
    public ArticleDTO getById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Article", "id: " + id));
        return ArticleMapper.toDto(article);
    }

    @Override
    public List<ArticleDTO> getAll() {
        return articleRepository.findAll().stream().map(ArticleMapper::toDto).toList();
    }

    @Override
    public List<ArticleDTO> getArticlesByCategory(String category) {

        return articleRepository.findByCategoryName(category).stream().map(ArticleMapper::toDto).toList();
    }

    @Override
    public List<ArticleDTO> getArticlesByDate(Date date) {
        return articleRepository.findByPublishedAt(date).stream().map(ArticleMapper::toDto).toList();
    }

    @Override
    public List<ArticleDTO> getArticlesByDateRange(Date from, Date to) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdOn");
        return articleRepository.findAllByPublishedAtBetween(from, to, sort).stream().map(ArticleMapper::toDto).toList();
    }

    @Override
    public ArticleDTO create(ArticleDTO articleDTO) {
        Article article = ArticleMapper.toEntity(articleDTO);
        return ArticleMapper.toDto(articleRepository.save(article));
    }

    @Override
    public ArticleDTO update(Long id, ArticleDTO articleDTO) {

        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Long id) {

        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<ArticleDTO> fetchExternalNews(){
        List<ArticleDTO> result = new ArrayList<>();
        List<NewsSourceDTO> newsSources=newsSourceService.getAllByEnabledAndUpdateLastModified();
        for (NewsSourceDTO newsSource : newsSources) {
            List<ArticleDTO> articleDTOs = fetcherService
                    .fetchAndConvert(newsSource.getSourceUrl() + newsSource.getSourceApiKey());
            result.addAll(articleDTOs);
        }
        return result;
    }

    @Override
    public List<ArticleDTO> searchArticlesByKeyword(String keyword) {
        List<Article> articles = articleRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword,
                keyword);
        return articles.stream().map(ArticleMapper::toDto).toList();
    }

    @Override
    public List<ArticleDTO> saveAllArticles(List<ArticleDTO> articleDTOs) {
        return articleRepository.saveAll(articleDTOs.stream().map(ArticleMapper::toEntity).toList()).stream()
                .map(ArticleMapper::toDto).toList();
    }

    @Override
    public List<ArticleDTO> getArticlesSortedByLikesAndDislikes() {
        List<Article> sortedArticles = articleRepository.findAllOrderByLikesAndDislikes(); 
        return sortedArticles.stream()
                .map(ArticleMapper::toDto)
                .toList();
    }

    @Override
    public void hideArticle(Long articleId) {
        articleRepository.hideArticleById(articleId);
    }

    @Override
    public void unHideArticle(Long articleId) {
        articleRepository.unhideArticleById(articleId);
    }

    @Override
    public void reportArticle(Long articleId, Long userId, String reason) {

        if (articleReportRepository.existsByArticleArticleIdAndReportedByUserId(articleId,userId)) {
            throw new AlreadyExistsException("Article Report","id");
        }

        ArticleReport report = new ArticleReport();
        Article article=new Article();
        report.setArticle(article);
        User user=new User();
        user.setUserId(userId);
        report.setReportedBy(user);
        report.setReason(reason);
        report.setReportedAt(new Date());

        articleReportRepository.save(report);

        long count = articleReportRepository.countByArticle(article);
        long REPORT_THRESHOLD=systemConfigService.getCurrentReportThreshold();
        if (count >= REPORT_THRESHOLD) {
          hideArticle(articleId);
        }
    }


}
