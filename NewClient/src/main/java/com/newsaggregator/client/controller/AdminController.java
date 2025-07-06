package com.newsaggregator.client.controller;

import com.newsaggregator.client.dto.ArticleDTO;
import com.newsaggregator.client.dto.CategoryDTO;
import com.newsaggregator.client.dto.NewsSourceDTO;
import com.newsaggregator.client.service.AdminService;
import com.newsaggregator.client.service.NewsService;
import com.newsaggregator.client.session.UserSession;
import com.newsaggregator.client.util.AdminMainMenu;
import com.newsaggregator.client.util.ConsoleUtils;
import com.newsaggregator.client.util.Constant;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.newsaggregator.client.util.ControllerUtil.promptChoice;
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
                case UPDATE_CATEGORY_STATUS -> {
                    toggleCategoryStatus();
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
                case LOGOUT -> false;
            };
        }
        return false;
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
        ConsoleUtils.readLine(PRESS_RETURN);
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
        ConsoleUtils.readLine(PRESS_RETURN);
    }

    private void updateNewsSourceApiKey() {
        long newsSourceId = ConsoleUtils.readLong(NEWS_SOURCE_ID_PROMPT);
        if (newsSourceId == 0) return;
        String apiKey = ConsoleUtils.readLine(NEW_API_KEY_PROMPT);
        adminService.updateNewsSourceApiKey(newsSourceId, apiKey);
    }


    private void addCategory() {
        String name = ConsoleUtils.readLine(NEW_CATEGORY_NAME_PROMPT);
        adminService.addCategory(name);
    }

    private void viewAllCategories() {
        System.out.println("\n" + CATEGORIES_HEADER);
        List<CategoryDTO> categories = adminService.allCategories();
        if (categories == null || categories.isEmpty()) {
            System.out.println(NO_CATEGORIES);
            return;
        }
        categories.forEach(c -> System.out.printf("%d. %s %s%n", c.getCategoryId(), c.getName(),
                c.isEnabled() ? ENABLED_LABEL : DISABLED_LABEL));
        ConsoleUtils.readLine(PRESS_RETURN);
    }

    private void toggleCategoryStatus() {

        long categoryId = ConsoleUtils.readLong(CATEGORY_ID_TOGGLE_PROMPT);
        if (categoryId == 0) return;
        boolean enabled = ConsoleUtils.readBoolean(ENABLE_CATEGORY_PROMPT);
        adminService.updateCategoryStatus(categoryId, enabled);
    }


    private void viewAllNewsArticles() {
        System.out.println("\n" + ARTICLES_HEADER);
        List<ArticleDTO> articles = newsService.allNewsArticles();
        if (articles == null || articles.isEmpty()) {
            System.out.println(NO_ARTICLES);
            return;
        }
        articles.forEach(article -> System.out.printf("%d. %s %s%n", article.getArticleId(), article.getTitle(), article.isEnabled() ? ENABLED_LABEL : DISABLED_LABEL));
        ConsoleUtils.readLine(PRESS_RETURN);
    }

    private void updateNewsArticleStatus() {

        long articleId = ConsoleUtils.readLong(ARTICLE_ID_UPDATE_PROMPT);
        if (articleId == 0) return;
        boolean enabled = ConsoleUtils.readBoolean(ENABLE_ARTICLE_PROMPT);
        adminService.updateNewsArticleStatus(articleId, enabled);
    }


    private void printWelcome() {
        System.out.printf("%nWelcome Admin, %s | Date: %s | Time: %s%n", session.getUserName(),
                LocalDate.now().format(Constant.dateFormatter), LocalTime.now().format(Constant.timeFormatter));
    }


}
