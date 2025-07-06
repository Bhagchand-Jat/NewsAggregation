package com.news_aggregation_system.service.news;

import com.news_aggregation_system.dto.*;
import com.news_aggregation_system.exception.AlreadyExistsException;
import com.news_aggregation_system.exception.NotFoundException;
import com.news_aggregation_system.mapper.ArticleMapper;
import com.news_aggregation_system.mapper.ArticleReportMapper;
import com.news_aggregation_system.model.Article;
import com.news_aggregation_system.repository.ArticleReportRepository;
import com.news_aggregation_system.repository.ArticleRepository;
import com.news_aggregation_system.service.admin.KeywordService;
import com.news_aggregation_system.service.admin.NewsSourceService;
import com.news_aggregation_system.service.admin.SystemConfigService;
import com.news_aggregation_system.service.news.pruning.AdminKeywordPruner;
import com.news_aggregation_system.service.news.scoring.ArticleScorer;
import com.news_aggregation_system.service.news.scoring.RankedArticle;
import com.news_aggregation_system.service.news.scoring.UserPrefs;
import com.news_aggregation_system.service.news.scoring.UserSignals;
import com.news_aggregation_system.service.user.ArticleReactionService;
import com.news_aggregation_system.service.user.ArticleReadHistoryService;
import com.news_aggregation_system.service.user.CategoryPreferenceService;
import com.news_aggregation_system.service.user.SavedArticleService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.news_aggregation_system.service.common.Constant.*;
import static com.news_aggregation_system.service.news.ArticleSpecification.adminEnabledOnly;
import static com.news_aggregation_system.service.news.ArticleSpecification.applyFilters;

@Service
public class NewsAggregationServiceImpl implements NewsAggregationService {

    private final ArticleRepository articleRepository;
    private final NewsSourceService newsSourceService;
    private final ExternalNewsFetcherService fetcherService;
    private final ArticleReportRepository articleReportRepository;
    private final ArticleReactionService articleReactionService;
    private final ArticleReadHistoryService articleReadHistoryService;
    private final SavedArticleService savedArticleService;
    private final SystemConfigService systemConfigService;
    private final CategoryPreferenceService categoryPreferenceService;
    private final KeywordService keywordService;

    public NewsAggregationServiceImpl(ArticleRepository articleRepository, KeywordService keywordService, NewsSourceService newsSourceService, ExternalNewsFetcherService fetcherService, ArticleReportRepository articleReportRepository, ArticleReactionService articleReactionService, ArticleReadHistoryService articleReadHistoryService, SavedArticleService savedArticleService, SystemConfigService systemConfigService, CategoryPreferenceService categoryPreferenceService) {
        this.articleRepository = articleRepository;
        this.keywordService = keywordService;
        this.newsSourceService = newsSourceService;
        this.fetcherService = fetcherService;
        this.articleReportRepository = articleReportRepository;
        this.articleReactionService = articleReactionService;
        this.articleReadHistoryService = articleReadHistoryService;
        this.savedArticleService = savedArticleService;
        this.systemConfigService = systemConfigService;
        this.categoryPreferenceService = categoryPreferenceService;
    }

    @Override
    public ArticleDTO getById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ARTICLE, ID + id));
        return ArticleMapper.toDto(article);
    }

    @Override
    public List<ArticleDTO> getAll() {
        return articleRepository.findAll().stream().map(ArticleMapper::toDto).toList();
    }


    @Transactional
    @Override
    public ArticleDTO create(ArticleDTO articleDTO) {
        Article article = ArticleMapper.toEntity(articleDTO);
        return ArticleMapper.toDto(articleRepository.save(article));
    }

    @Transactional
    @Override
    public ArticleDTO update(Long id, ArticleDTO articleDTO) {

        throw new UnsupportedOperationException(UNIMPLEMENTED_UPDATE);
    }

    @Transactional
    @Override
    public void delete(Long id) {

        throw new UnsupportedOperationException(UNIMPLEMENTED_DELETE);
    }

    @Transactional
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


    @Transactional
    @Override
    public List<ArticleDTO> saveAllArticles(List<ArticleDTO> articleDTOs) {
        List<ArticleDTO> toBeRemovedArticles = new ArrayList<>();
        for (ArticleDTO articleDTO : articleDTOs) {
            if (articleRepository.existsByTitle(articleDTO.getTitle())) {
                toBeRemovedArticles.add(articleDTO);
            }
        }
        articleDTOs.removeAll(toBeRemovedArticles);
        return articleRepository.saveAll(articleDTOs.stream().map(ArticleMapper::toEntity).toList()).stream()
                .map(ArticleMapper::toDto).toList();
    }

    @Transactional
    @Override
    public void updateArticleStatusById(Long articleId, boolean enabled) {
        int updated = articleRepository.updateArticleStatusById(articleId, enabled);
        if (updated < 1) {
            throw new NotFoundException(ARTICLE, ID + articleId);
        }
    }

    @Transactional
    @Override
    public void reportArticle(ArticleReportDTO articleReportDTO) {
        Long articleId = articleReportDTO.getArticleId();
        Long userId = articleReportDTO.getUserId();

        if (articleReportRepository.existsByArticleArticleIdAndReportedByUserId(articleId, userId)) {
            throw new AlreadyExistsException(ARTICLE_ALREADY_REPORTED);
        }

        articleReportRepository.save(ArticleReportMapper.toEntity(articleReportDTO));

        long count = articleReportRepository.countByArticleArticleId(articleId);
        long REPORT_THRESHOLD = systemConfigService.getCurrentReportThreshold();
        if (count > REPORT_THRESHOLD) {
            updateArticleStatusById(articleId, false);
        }
    }

    @Override
    public List<ArticleDTO> getAllReportedArticles() {
        return articleReportRepository.getAllReportedArticles().stream().map(ArticleMapper::toDto).toList();
    }

    @Override
    public List<ArticleReportDTO> getAllArticleReportsByArticleId(Long articleId) {
        return articleReportRepository.findArticleReportByArticleArticleId(articleId)
                .stream()
                .map(ArticleReportMapper::toDTO)
                .toList();
    }

    @Override
    public List<ArticleReportDTO> getAllArticlesReportsByUserId(Long userId) {
        return articleReportRepository.findArticleReportByReportedByUserId(userId).stream().map(ArticleReportMapper::toDTO).toList();
    }

    @Override
    public List<ArticleDTO> filterArticles(Long userId, ArticleFilterRequestDTO filter) {

        validateRangeCompleteness(filter);
        validateExclusiveFilters(filter);

        List<Article> adminApproved = articleRepository.findAll(
                Specification.allOf(adminEnabledOnly()).and(applyFilters(filter)));

        Set<String> disabledKeywords = keywordService.findByEnabledFalse().stream()
                .map(keyword -> keyword.getName().toLowerCase(Locale.ROOT))
                .collect(Collectors.toSet());

        List<Article> articlesAfterPrune = AdminKeywordPruner.prune(adminApproved, disabledKeywords);

        UserPrefs userPrefs = loadUserPrefs(userId);
        UserSignals userSignals = loadUserSignals(userId);

        return articlesAfterPrune.stream()
                .map(article -> new RankedArticle(article,
                        ArticleScorer.score(article, userPrefs, userSignals)))
                .sorted(RankedArticle.comparator())
                .map(rankedArticle -> ArticleMapper.toDto(rankedArticle.article()))
                .toList();
    }

    private UserPrefs loadUserPrefs(Long userId) {
        Set<Long> allowedUserCategoryIds = categoryPreferenceService.getAllEnabledCategoriesPreference(userId).stream()
                .map(UserCategoryPreferenceDTO::getCategoryId)
                .collect(Collectors.toSet());

        Map<Long, Set<String>> allowedUserKeywordsByUserCategoryId = categoryPreferenceService.getAllEnabledKeywordPreferences(userId).stream()
                .collect(Collectors.groupingBy(
                        UserKeywordPreferenceDTO::getCategoryId,
                        Collectors.mapping(userKeywordPreference -> userKeywordPreference.getKeyword().toLowerCase(Locale.ROOT), Collectors.toSet())
                ));

        return new UserPrefs(allowedUserCategoryIds, allowedUserKeywordsByUserCategoryId);
    }

    private UserSignals loadUserSignals(Long userId) {
        return new UserSignals(
                articleReactionService.getArticleIdsLikedByUser(userId),
                savedArticleService.getArticleIdsSavedByUser(userId),
                articleReadHistoryService.getArticleIdsReadByUser(userId)
        );
    }

    private void validateRangeCompleteness(ArticleFilterRequestDTO filter) {
        if ((filter.getFrom() != null) ^ (filter.getTo() != null))
            throw new IllegalArgumentException(FROM_TO_PROVIDED_TOGETHER);
    }

    private void validateExclusiveFilters(ArticleFilterRequestDTO filter) {
        int noOfFilters = bool(has(filter.getKeyword())) + bool(filter.getDate() != null) + bool(filter.getCategoryId() != null) + bool(isRange(filter));
        if (isRange(filter) && filter.getCategoryId() != null) noOfFilters = 1;
        if (noOfFilters > 1) throw new IllegalArgumentException(PROVIDE_ONLY_ONE_FILTER);
    }

    private boolean isRange(ArticleFilterRequestDTO f) {
        return f.getFrom() != null && f.getTo() != null;
    }

    private boolean has(String s) {
        return s != null && !s.isBlank();
    }

    private int bool(boolean b) {
        return b ? 1 : 0;
    }

}
