package com.news_aggregation_system.service.news;

import com.news_aggregation_system.converter.external.ExternalArticleConverter;
import com.news_aggregation_system.dto.ArticleDTO;
import com.news_aggregation_system.exception.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExternalNewsFetcherServiceImpl implements ExternalNewsFetcherService{
    private final Logger logger = LoggerFactory.getLogger(ExternalNewsFetcherServiceImpl.class);
    private final RestTemplate restTemplate;
    private final List<ExternalArticleConverter> converters;

    public ExternalNewsFetcherServiceImpl(RestTemplate restTemplate, List<ExternalArticleConverter> converters) {
        this.restTemplate = restTemplate;
        this.converters = converters;
    }

    @Override
    public List<ArticleDTO> fetchAndConvert(String url) {
        
                try {
                    String response = restTemplate.getForObject(url, String.class);

       ExternalArticleConverter externalArticleConverter=  converters.stream()
                .filter(c -> c.supports(url))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Converter", "URL: " + url));
       logger.info(externalArticleConverter.toString());
                    return externalArticleConverter.convert(response);
                } catch (Exception e) {
                    logger.info(e.getMessage());
                   return new ArrayList<>();
                }
                
    }
}

