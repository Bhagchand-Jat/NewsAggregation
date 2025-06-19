package com.news_aggregation_system.service.news;

import com.news_aggregation_system.dto.ArticleDTO;
import com.news_aggregation_system.dto.NewsSourceDTO;
import com.news_aggregation_system.exception.NotFoundException;
import com.news_aggregation_system.mapper.ArticleMapper;
import com.news_aggregation_system.model.Article;
import com.news_aggregation_system.model.NewsSource;
import com.news_aggregation_system.repository.ArticleRepository;
import com.news_aggregation_system.repository.NewsSourceRepository;

import com.news_aggregation_system.service.admin.NewsSourceService;
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
    



    public NewsAggregationServiceImpl(ArticleRepository articleRepository,
            NewsSourceService newsSourceService, ExternalNewsFetcherService fetcherService) {
        this.articleRepository = articleRepository;
        this.newsSourceService = newsSourceService;
        this.fetcherService = fetcherService;
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


}
