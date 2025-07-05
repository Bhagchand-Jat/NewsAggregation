package com.newsaggregator.client.controller;

import com.newsaggregator.client.dto.*;
import com.newsaggregator.client.model.Keyword;
import com.newsaggregator.client.service.AdminService;
import com.newsaggregator.client.service.NewsService;
import com.newsaggregator.client.session.UserSession;
import com.newsaggregator.client.util.AdminMainMenu;
import com.newsaggregator.client.util.CategoryMenu;
import com.newsaggregator.client.util.ConsoleUtils;
import com.newsaggregator.client.util.Constant;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.newsaggregator.client.util.Constant.simpleDateFormatter;
import static com.newsaggregator.client.util.ControllerUtil.*;
import static com.newsaggregator.client.util.UiText.*;

public class AdminController {

    private final AdminService adminService;
    private final NewsService newsService;
    private final UserSession session;

    public AdminController(AdminService adminService, NewsService newsService, UserSession session) {
        this.adminService = Objects.requireNonNull(adminService);
        this.newsService = Objects.requireNonNull(newsService);
        this.session = Objects.requireNonNull(session);
    }


    public boolean start() {
        printWelcome();
        boolean running = true;
        while (running) {
            running = switch (promptChoice(AdminMainMenu.values(), CHOOSE_OPTION_BELOW)) {
                case SOURCES_STATUS -> {
                    viewNewsSourcesStatuses();
                    yield true;
                }
                case SOURCE_DETAILS -> {
                    viewNewsSourcesDetails();
                    yield true;
                }
                case UPDATE_SOURCE_KEY -> {
                    updateNewsSourceApiKey();
                    yield true;
                }

                case ADD_CATEGORY -> {
                    addCategory();
                    yield true;
                }
                case VIEW_CATEGORIES -> {
                    viewAllCategories();
                    yield true;
                }
                case VIEW_ARTICLES -> {
                    viewAllNewsArticles();
                    yield true;
                }
                case UPDATE_ARTICLE_STATUS -> {
                    updateNewsArticleStatus();
                    yield true;
                }
                case VIEW_REPORTED_ARTICLES -> {
                    viewReportedArticles();
                    yield true;
                }
                case VIEW_ARTICLE_REPORT_THRESHOLD -> {
                    viewArticleReportThreshold();
                    yield true;
                }
                case UPDATE_ARTICLE_REPORT_THRESHOLD -> {
                    updateArticleReportThreshold();
                    yield true;
                }
                case LOGOUT -> false;
            };
        }
        return false;
    }

    private void viewReportedArticles() {
        List<ArticleDTO> reportedArticles = adminService.getAllReportedArticles();
        printArticles(reportedArticles);

        if (!reportedArticles.isEmpty()) {
            showArticleReports(reportedArticles);
        }
    }

    private void showArticleReports(List<ArticleDTO> reportedArticles) {
        int articleIndexChoice = readIntSafe("\n" + SELECT_ONE_ARTICLE_TO_SHOW_REASONS_OR_BACK_OPTION);
        if (articleIndexChoice == 0) {
            return;
        }
        if (articleIndexChoice > 0 && articleIndexChoice <= reportedArticles.size()) {

            List<ArticleReportDTO> reports = adminService.getAllReportsOfArticle(reportedArticles.get(articleIndexChoice - 1).getArticleId());
            printIndexed(reports, articleReportDTO -> articleReportDTO.getReason() + " - " + Optional.ofNullable(articleReportDTO.getReportedAt())
                    .map(simpleDateFormatter::format)
                    .orElse(NA));
            showArticleReports(reportedArticles);
        } else {
            System.out.println(INVALID);
            showArticleReports(reportedArticles);
        }
    }

    private void updateArticleReportThreshold() {

        int thresholdInput = readIntSafe(REPORT_THRESHOLD_PROMPT);

        adminService.updateArticleReportThreshold(thresholdInput);
    }

    private void viewArticleReportThreshold() {
        Long threshold = adminService.getArticleReportThreshold();
        if (threshold != null) {
            System.out.println("Threshold: " + threshold);
        }
    }


    private void viewNewsSourcesStatuses() {
        System.out.println("\n" + SOURCES_HEADER);
        List<NewsSourceDTO> sources = adminService.allNewsSources();
        if (sources == null || sources.isEmpty()) {
            System.out.println(NO_SOURCES);
            return;
        }
        sources.forEach(src -> System.out.printf("%d. %s - %s - " + LAST_ACCESSED + " %s%n",
                src.getSourceId(),
                src.getSourceName(),
                src.isEnabled() ? ACTIVE_STATUS : INACTIVE_STATUS,
                Optional.ofNullable(src.getLastAccessed())
                        .map(d -> d.format(Constant.dateFormatter))
                        .orElse(NA)));
        pause();
    }

    private void viewNewsSourcesDetails() {
        System.out.println("\n" + SOURCE_DETAILS_HEADER);
        List<NewsSourceDTO> sources = adminService.allNewsSources();
        if (sources == null || sources.isEmpty()) {
            System.out.println(NO_SOURCES);
            return;
        }
        sources.forEach(newsSource -> System.out.printf("%d. %s - " + API_KEY + " %s%n",
                newsSource.getSourceId(), newsSource.getSourceName(), newsSource.getSourceApiKey()));
        pause();
    }

    private void updateNewsSourceApiKey() {
        long newsSourceId = ConsoleUtils.readLong(NEWS_SOURCE_ID_PROMPT);
        if (newsSourceId == 0) return;
        String apiKey = ConsoleUtils.readLine(NEW_API_KEY_PROMPT);
        adminService.updateNewsSourceApiKey(newsSourceId, apiKey);
        pause();
    }


    private void addCategory() {
        String name = ConsoleUtils.readLine(NEW_CATEGORY_NAME_PROMPT);
        adminService.addCategory(name);
        pause();
    }

    private void viewAllCategories() {
        System.out.println("\n" + CATEGORIES_HEADER);
        List<CategoryDTO> categories = adminService.allCategories();
        if (categories == null || categories.isEmpty()) {
            System.out.println(NO_CATEGORIES);
            return;
        }
        printIndexed(categories, category -> category.getName() + " "
                + (category.isEnabled() ? ENABLED_LABEL : DISABLED_LABEL)
                + (!category.getKeywords().isEmpty() ? "\n keywords ---> " + category.getKeywords().stream()
                .map(Keyword::getName).collect(Collectors.joining(",")) : ""));
        pause();

        selectOneCategoryMenu(categories);

    }

    private void selectOneCategoryMenu(List<CategoryDTO> categories) {
        int categoryIndex = readIntSafe(SELECT_ONE_CATEGORY_OR_BACK_OPTION);
        if (categoryIndex == 0) {
            return;
        }
        if (!invalidIndex(categoryIndex, categories.size())) {
            CategoryDTO category = categories.get(categoryIndex - 1);
            categoryMenu(category);
        } else {
            selectOneCategoryMenu(categories);
        }
    }

    private void categoryMenu(CategoryDTO category) {
        while (true) {
            switch (promptChoice(CategoryMenu.values(), CHOOSE_OPTION_BELOW)) {
                case UPDATE_CATEGORY_STATUS -> updateCategoryStatus(category.getCategoryId(), !category.isEnabled());
                case ADD_KEYWORDS -> addKeywords(category.getCategoryId());
                case VIEW_KEYWORDS -> viewKeywordsForCategory(category.getCategoryId());
                case UPDATE_KEYWORD_STATUS -> updateKeywordStatusView(category.getCategoryId());
                case DELETE_KEYWORD -> deleteKeyword(category.getCategoryId());
                case BACK -> {
                    return;
                }

            }
        }
    }

    private void viewKeywordsForCategory(Long categoryId) {
        List<KeywordDTO> keywords = adminService.getAllKeywordsForCategory(categoryId);
        if (keywords == null || keywords.isEmpty()) {
            System.out.println("\n" + NO_KEYWORDS);
            pause();
            return;
        }
        printIndexed(keywords, KeywordDTO::getName);
        pause();
    }

    private void deleteKeyword(Long categoryId) {
        String keywordInput = ConsoleUtils.readLine(ENTER_KEYWORD_TO_DELETE);
        if (keywordInput.isEmpty()) {
            System.out.println("\n" + NO_KEYWORD_INPUT + "\n");
            deleteKeyword(categoryId);
        }
        adminService.deleteKeyword(categoryId, keywordInput);
        pause();
    }

    private void updateKeywordStatusView(Long categoryId) {
        String keywordInput = ConsoleUtils.readLine(ENTER_KEYWORD_TO_UPDATE);
        boolean isEnabled = ConsoleUtils.readBoolean(ENABLE_KEYWORD_PROMPT);

        adminService.updateKeywordStatus(categoryId, keywordInput, isEnabled);
        pause();

    }

    private void addKeywords(Long categoryId) {
        String keywords = ConsoleUtils.readLine(ENTER_KEYWORDS);
        adminService.addKeywordsToCategory(categoryId, Arrays.stream(keywords.split(",")).toList());
        pause();

    }

    private void updateCategoryStatus(Long categoryId, boolean isEnabled) {

        adminService.updateCategoryStatus(categoryId, isEnabled);
        pause();
    }


    private void viewAllNewsArticles() {
        System.out.println("\n" + ARTICLES_HEADER);
        List<ArticleDTO> articles = newsService.allNewsArticles();
        printArticles(articles);
    }

    private void updateNewsArticleStatus() {

        long articleId = ConsoleUtils.readLong(ARTICLE_INDEX_UPDATE_PROMPT);
        if (articleId == 0) return;
        boolean enabled = ConsoleUtils.readBoolean(ENABLE_ARTICLE_PROMPT);
        adminService.updateNewsArticleStatus(articleId, enabled);
        pause();
    }


    private void printWelcome() {
        System.out.printf("%nWelcome Admin, %s | Date: %s | Time: %s%n", session.getUserName(),
                LocalDate.now().format(Constant.dateFormatter), LocalTime.now().format(Constant.timeFormatter));
    }

    private void printArticles(List<ArticleDTO> articles) {
        if (articles == null || articles.isEmpty()) {
            System.out.println(NO_ARTICLES);
            pause();
            return;
        }
        printIndexed(articles, article -> article.getTitle() + " - " + (article.isEnabled() ? ENABLED_LABEL : DISABLED_LABEL) + "\n     ---> " + LIKES + article.getLikeCount() + DISLIKES + article.getDislikeCount() + TOTAL_REPORT + article.getReportCount());
        pause();

    }


}
