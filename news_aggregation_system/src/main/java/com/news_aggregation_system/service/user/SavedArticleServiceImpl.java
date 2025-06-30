package com.news_aggregation_system.service.user;

import com.news_aggregation_system.dto.ArticleDTO;
import com.news_aggregation_system.dto.SavedArticleDTO;
import com.news_aggregation_system.exception.AlreadyExistsException;
import com.news_aggregation_system.exception.NotFoundException;
import com.news_aggregation_system.mapper.ArticleMapper;
import com.news_aggregation_system.mapper.SavedArticleMapper;
import com.news_aggregation_system.model.Article;
import com.news_aggregation_system.model.SavedArticle;
import com.news_aggregation_system.model.SavedArticleId;
import com.news_aggregation_system.model.User;
import com.news_aggregation_system.repository.ArticleRepository;
import com.news_aggregation_system.repository.SavedArticleRepository;
import com.news_aggregation_system.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class SavedArticleServiceImpl implements SavedArticleService {

    private final SavedArticleRepository savedArticleRepository;

    private final UserRepository userRepository;

    private final ArticleRepository articleRepository;

    public SavedArticleServiceImpl(SavedArticleRepository savedArticleRepository, UserRepository userRepository, ArticleRepository articleRepository) {
        this.savedArticleRepository = savedArticleRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public List<ArticleDTO> getSavedArticlesByUser(Long userId) {
        return savedArticleRepository.findByUserUserId(userId).stream()
                .map(savedArticle -> ArticleMapper.toDto(savedArticle.getArticle()))
                .collect(Collectors.toList());
    }

    @Override
    public void saveArticle(SavedArticleDTO savedArticleDTO) {
        User user = userRepository.findById(savedArticleDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("User", "id: " + savedArticleDTO.getUserId()));

        Article article = articleRepository.findById(savedArticleDTO.getArticleId())
                .orElseThrow(() -> new NotFoundException("Article", "id: " + savedArticleDTO.getArticleId()));
        boolean isAlreadyExist = savedArticleRepository.existsSavedArticleByArticleArticleIdAndUserUserId(savedArticleDTO.getArticleId(), savedArticleDTO.getUserId());
        if (isAlreadyExist) {
            throw new AlreadyExistsException("Article already saved for this user.");
        }
        SavedArticle saved = SavedArticleMapper.toEntity(savedArticleDTO, user, article);
        saved.setId(new SavedArticleId(user.getUserId(), article.getArticleId()));

        SavedArticleMapper.toDTO(savedArticleRepository.save(saved));
    }

    @Override
    public void deleteSavedArticle(Long userId, Long articleId) {
        SavedArticleId id = new SavedArticleId(userId, articleId);
        savedArticleRepository.deleteById(id);
    }


}
