package com.news_aggregation_system.service.news;

import com.news_aggregation_system.dto.ArticleDTO;
import com.news_aggregation_system.dto.ArticleFilterRequestDTO;
import com.news_aggregation_system.dto.ArticleReportDTO;
import com.news_aggregation_system.dto.NewsSourceDTO;
import com.news_aggregation_system.exception.AlreadyExistsException;
import com.news_aggregation_system.exception.NotFoundException;
import com.news_aggregation_system.mapper.ArticleMapper;
import com.news_aggregation_system.mapper.ArticleReportMapper;
import com.news_aggregation_system.model.Article;
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
import java.util.Optional;

@Transactional
@Service
public class NewsAggregationServiceImpl implements NewsAggregationService {

    private final ArticleRepository articleRepository;
    private final NewsSourceService newsSourceService;
    private final ExternalNewsFetcherService fetcherService;
    private final ArticleReportRepository articleReportRepository;
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
        return articleRepository.findByPublishedAtDate(date).stream().map(ArticleMapper::toDto).toList();
    }

    @Override
    public List<ArticleDTO> getArticlesByDateRange(Date from, Date to) {
        Sort sort = Sort.by(Sort.Direction.ASC, "publishedAt");
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
    public List<ArticleDTO> fetchExternalNews() {
        List<ArticleDTO> result = new ArrayList<>();
        List<NewsSourceDTO> newsSources = newsSourceService.getAllByEnabledAndUpdateLastModified();
        for (NewsSourceDTO newsSource : newsSources) {
            List<ArticleDTO> articleDTOs = fetcherService
                    .fetchAndConvert(newsSource.getSourceUrl(), newsSource.getSourceApiKey());
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
    public void updateArticleStatusById(Long articleId, boolean enabled) {
        int updated = articleRepository.updateArticleStatusById(articleId, enabled);
        if (updated < 1) {
            throw new NotFoundException("Article", "id: " + articleId);
        }
    }


    @Override
    public void reportArticle(ArticleReportDTO articleReportDTO) {
        Long articleId = articleReportDTO.getArticleId();
        Long userId = articleReportDTO.getUserId();

        if (articleReportRepository.existsByArticleArticleIdAndReportedByUserId(articleId, userId)) {
            throw new AlreadyExistsException("Article already Reported By User");
        }

        articleReportRepository.save(ArticleReportMapper.toEntity(articleReportDTO));

        long count = articleReportRepository.countByArticleArticleId(articleId);
        long REPORT_THRESHOLD = systemConfigService.getCurrentReportThreshold();
        if (count >= REPORT_THRESHOLD) {
            updateArticleStatusById(articleId, true);
        }
    }

    @Override
    public List<ArticleReportDTO> getAllArticleReportsByArticleId(Long articleId) {
        return articleReportRepository.findArticleReportByArticleArticleId(articleId)
                .stream()
                .map(ArticleReportMapper::toDTO)
                .toList();
    }

    @Override
    public Optional<ArticleReportDTO> getArticleReportByArticleIdAndUserId(Long articleId, Long userId) {
        return Optional.ofNullable(articleReportRepository.findArticleReportByArticleArticleIdAndReportedByUserId(articleId, userId).map(ArticleReportMapper::toDTO).orElseThrow(() -> new NotFoundException("Article Report", "id: " + articleId)));
    }

    @Override
    public List<ArticleReportDTO> getAllArticlesReportsByUserId(Long userId) {
        return articleReportRepository.findArticleReportByReportedByUserId(userId).stream().map(ArticleReportMapper::toDTO).toList();
    }

    @Override
    public List<ArticleDTO> filterArticles(ArticleFilterRequestDTO filterRequestDTO) {

        validateRangeCompleteness(filterRequestDTO);
        validateExclusiveFilters(filterRequestDTO);

        if (hasText(filterRequestDTO.getKeyword())) return searchArticlesByKeyword(filterRequestDTO.getKeyword());
        if (filterRequestDTO.getDate() != null) return getArticlesByDate(filterRequestDTO.getDate());
        if (isRangeWithCategory(filterRequestDTO))
            return articleRepository.getArticlesByDateRangeAndCategory(filterRequestDTO.getFrom(), filterRequestDTO.getTo(), filterRequestDTO.getCategoryId()).stream().map(ArticleMapper::toDto).toList();
        if (isRange(filterRequestDTO))
            return getArticlesByDateRange(filterRequestDTO.getFrom(), filterRequestDTO.getTo());
        if (filterRequestDTO.getCategoryId() != null)
            return articleRepository.getArticlesByCategoryId(filterRequestDTO.getCategoryId()).stream().map(ArticleMapper::toDto).toList();

        return getAll();
    }

    private void validateRangeCompleteness(ArticleFilterRequestDTO filterRequestDTO) {
        boolean exactlyOneDateBound = (filterRequestDTO.getFrom() != null) ^ (filterRequestDTO.getTo() != null);
        if (exactlyOneDateBound) {
            throw new IllegalArgumentException("'from' and 'to' must be provided together");
        }
    }

    private void validateExclusiveFilters(ArticleFilterRequestDTO f) {
        int criteria =
                boolToInt(hasText(f.getKeyword())) +
                        boolToInt(f.getDate() != null) +
                        boolToInt(f.getCategoryId() != null) +
                        boolToInt(isRange(f));

        if (isRangeWithCategory(f)) criteria = 1;

        if (criteria > 1) {
            throw new IllegalArgumentException("Provide only one filter, or 'from'+'to' with 'categoryId'");
        }
    }

    private boolean isRange(ArticleFilterRequestDTO f) {
        return f.getFrom() != null && f.getTo() != null;
    }

    private boolean isRangeWithCategory(ArticleFilterRequestDTO f) {
        return isRange(f) && f.getCategoryId() != null;
    }

    private boolean hasText(String s) {
        return s != null && !s.isBlank();
    }

    private int boolToInt(boolean value) {
        return value ? 1 : 0;
    }


}
