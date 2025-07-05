package com.newsaggregator.client.controller;

import com.newsaggregator.client.dto.*;
import com.newsaggregator.client.service.NewsService;
import com.newsaggregator.client.service.NotificationService;
import com.newsaggregator.client.session.UserSession;
import com.newsaggregator.client.util.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Consumer;

import static com.newsaggregator.client.util.ControllerUtil.*;
import static com.newsaggregator.client.util.UiText.*;

public class UserController {

    private final NewsService newsService;
    private final NotificationService notificationService;
    private final UserSession session;

    public UserController(NewsService newsService,
                          NotificationService notificationService,
                          UserSession session) {
        this.newsService = Objects.requireNonNull(newsService);
        this.notificationService = Objects.requireNonNull(notificationService);
        this.session = Objects.requireNonNull(session);
    }

    public boolean start() {
        printWelcome();
        boolean running = true;
        while (running) {
            running = switch (promptChoice(UserMainMenu.values(), CHOOSE_OPTION_BELOW)) {
                case HEADLINES -> {
                    headlinesMenu();
                    yield true;
                }
                case SAVED_ARTICLES -> {
                    savedArticlesMenu();
                    yield true;
                }
                case SEARCH -> {
                    searchArticlesMenu();
                    yield true;
                }
                case REPORTS -> {
                    showReports();
                    yield true;
                }
                case NOTIFICATIONS -> {
                    notificationMenu();
                    yield true;
                }
                case ARTICLES_READ_HISTORY -> {
                    showArticleReadHistory();
                    yield true;
                }
                case LOGOUT -> false;
            };
        }
        return false;

    }

    private void printWelcome() {
        System.out.printf("%n" + WELCOME_USER_HEADER + ", %s | Date: %s | Time: %s%n",
                session.getUserName(),
                LocalDate.now().format(Constant.dateFormatter),
                LocalTime.now().format(Constant.timeFormatter));
    }


    private void headlinesMenu() {
        switch (promptChoice(HeadlineMenu.values(), CHOOSE_OPTION_BELOW)) {
            case TODAY -> showHeadlines(newsService.todayNewsArticles(session.getUserId()));
            case DATE_RANGE -> dateRangeHeadlinesMenu();
            case BACK -> {
            }
        }
    }

    private void showHeadlines(List<ArticleDTO> articles) {
        System.out.println("\n" + HEADLINES_HEADER + "\n");
        displayArticles(articles, this::headlinesActions);
    }

    private void dateRangeHeadlinesMenu() {
        String input = ConsoleUtils.readLine(DATE_RANGE_PROMPT);
        String[] parts = input.split(":");
        if (parts.length != 2) {
            System.out.println(DATE_RANGE_FORMAT);
            return;
        }

        try {
            Date from = Constant.toUtilDate(parts[0]);
            Date to = Constant.toUtilDate(parts[1]);

            List<CategoryDTO> categories = newsService.getCategories();
            if (categories == null || categories.isEmpty()) {
                System.out.println(NO_CATEGORIES);
                return;
            }

            printIndexed(categories, CategoryDTO::getName);

            int choice = readIntSafe(SELECT_CATEGORY_INDEX_TO_SHOW_HEADINGS);
            if (choice == 0 || invalidIndex(choice, categories.size())) return;

            CategoryDTO categoryDTO = categories.get(choice - 1);
            showHeadlines(newsService.fetchHeadlines(from, to, categoryDTO.getCategoryId(), session.getUserId()));

        } catch (Exception e) {
            System.out.println(INVALID_DATE_FORMAT);
        }
    }


    private void savedArticlesMenu() {
        displayArticles(newsService.getSavedArticles(session.getUserId()), this::savedArticleActions);
    }

    private void searchArticlesMenu() {
        String query = ConsoleUtils.readLine(SEARCH_QUERY_PROMPT);
        System.out.printf("%n%s '%s'%n", SEARCH_ARTICLES, query);
        displayArticles(newsService.searchArticles(query, session.getUserId()), this::searchArticleActions);
    }


    private void displayArticles(List<ArticleDTO> articles, Consumer<ArticleDTO> onSelect) {
        if (articles.isEmpty()) {
            System.out.println(NO_ARTICLES);
            pause();
            return;
        }

        while (true) {
            printIndexed(articles, ArticleDTO::getTitle);

            int choice = readIntSafe("\n" + SELECT_ONE_ARTICLE_OR_BACK_OPTION);
            if (choice == 0) return;
            if (invalidIndex(choice, articles.size())) {
                continue;
            }
            onSelect.accept(articles.get(choice - 1));
        }
    }


    private void articleActionMenu(ArticleDTO articleDTO, Action[] actions, String header) {
        showFullArticle(articleDTO);

        while (true) {
            switch (promptChoice(actions, header)) {
                case SAVE -> newsService.saveArticle(session.getUserId(), articleDTO.getArticleId());
                case REPORT -> reportArticle(articleDTO);
                case LIKE -> newsService.likeArticle(articleDTO.getArticleId(), session.getUserId());
                case DISLIKE -> newsService.disLikeArticle(articleDTO.getArticleId(), session.getUserId());
                case DELETE -> {
                    if (newsService.deleteSavedArticle(session.getUserId(), articleDTO.getArticleId())) return;
                }
                case BACK -> {
                    return;
                }
            }
        }
    }


    private void notificationMenu() {
        while (true) {
            switch (promptChoice(NotificationMenu.values(), NOTIFICATIONS_HEADER)) {
                case VIEW -> showNotifications();
                case CONFIGURE -> configureNotificationMenu();
                case BACK -> {
                    return;
                }
            }
        }
    }

    private void configureNotificationMenu() {
        List<CategoryStatusDTO> categoriesStatus = notificationService.getCategoriesStatus(session.getUserId());
        if (categoriesStatus == null || categoriesStatus.isEmpty()) {
            System.out.println(NO_CATEGORIES);
            pause();
            return;
        }

        while (true) {
            System.out.println("\n" + CONFIGURE_NOTIFICATIONS_HEADER + "\n");
            printIndexed(categoriesStatus, categoryStatusDTO -> categoryStatusDTO.getName() + " – " + (categoryStatusDTO.isEnabled() ? ENABLED_LABEL : DISABLED_LABEL));

            int choice = readIntSafe(SELECT_ONE_CATEGORY_OR_BACK_OPTION);
            if (choice == 0) return;
            if (!invalidIndex(choice, categoriesStatus.size())) {
                categoryMenu(categoriesStatus.get(choice - 1));
            }

        }
    }

    private void categoryMenu(CategoryStatusDTO categoryStatus) {
        while (true) {
            switch (promptChoice(categoryActions(), CATEGORY_UPDATE_PROMPT.replace("%s", categoryStatus.getName()))) {
                case UPDATE_CATEGORY_STATUS -> notificationService.updateCategoryStatus(session.getUserId(),
                        categoryStatus.getCategoryId(), !categoryStatus.isEnabled());
                case ADD_KEYWORDS -> addKeywordsToCategory(categoryStatus.getCategoryId());
                case VIEW_KEYWORDS -> viewKeywordsForCategory(categoryStatus.getCategoryId());
                case DELETE_KEYWORD -> deleteKeywordFromCategory(categoryStatus.getCategoryId());
                case BACK -> {
                    return;
                }
            }


        }
    }

    private void viewKeywordsForCategory(Long categoryId) {
        List<String> keywords = notificationService.viewKeywordsForCategory(session.getUserId(), categoryId);
        if (keywords == null || keywords.isEmpty()) {
            System.out.println("\n" + NO_KEYWORDS);
            pause();
            return;
        }
        printIndexed(keywords, keyword -> keyword);
        pause();
    }

    private void deleteKeywordFromCategory(Long categoryId) {
        String keyword = ConsoleUtils.readLine(UiText.ENTER_KEYWORD_TO_DELETE);
        notificationService.deleteUserKeyword(session.getUserId(), categoryId, keyword);
    }

    private void showNotifications() {
        List<NotificationDTO> notifications = notificationService.viewNotifications(session.getUserId());
        if (notifications == null || notifications.isEmpty()) {
            System.out.println(NO_NOTIFICATIONS);
            pause();
            return;
        }
        notifications.forEach(n -> System.out.printf("* %s%n", n.getMessage()));
        pause();
    }

    private void showReports() {
        List<ArticleReportDTO> reports = newsService.getUserReports(session.getUserId());
        if (reports == null || reports.isEmpty()) {
            System.out.println(NO_REPORTS);
            pause();
            return;
        }
        reports.forEach(report -> System.out.printf("ReportId:%d | ArticleId:%d | Reason:%s%n", report.getArticleId(), report.getId(), report.getReason()));
        pause();
    }


    private void headlinesActions(ArticleDTO articleDTO) {
        EnumSet<Action> actionsSet = EnumSet.of(Action.SAVE, Action.REPORT, Action.LIKE, Action.DISLIKE, Action.BACK);
        Action[] actions = actionsSet.toArray(new Action[0]);
        articleActionMenu(articleDTO, actions, HEADLINES_HEADER);
    }

    private void savedArticleActions(ArticleDTO articleDTO) {
        EnumSet<Action> actionsSet = EnumSet.of(Action.DELETE, Action.BACK);
        Action[] actions = actionsSet.toArray(new Action[0]);
        articleActionMenu(articleDTO, actions, SAVED_ARTICLES_HEADER);
    }

    private void searchArticleActions(ArticleDTO articleDTO) {
        EnumSet<Action> actionsSet = EnumSet.of(Action.SAVE, Action.BACK);
        Action[] actions = actionsSet.toArray(new Action[0]);
        articleActionMenu(articleDTO, actions, SEARCH_ARTICLES_HEADER);
    }

    private CategoryMenu[] categoryActions() {
        EnumSet<CategoryMenu> categorySet = EnumSet.of(CategoryMenu.UPDATE_CATEGORY_STATUS, CategoryMenu.ADD_KEYWORDS, CategoryMenu.VIEW_KEYWORDS, CategoryMenu.BACK);
        return categorySet.toArray(new CategoryMenu[0]);
    }

    private void addKeywordsToCategory(Long categoryId) {
        String keywords = ConsoleUtils.readLine(ENTER_KEYWORDS);
        notificationService.addKeywordsToCategory(session.getUserId(),
                categoryId, Arrays.stream(keywords.split(",")).map(String::trim).toList());
    }

    private void showFullArticle(ArticleDTO articleDTO) {
        newsService.markArticleAsRead(session.getUserId(), articleDTO.getArticleId());
        System.out.printf("%nID: %d%nTitle: %s%nDescription: %s%n",
                articleDTO.getArticleId(), articleDTO.getTitle(), articleDTO.getDescription());
        System.out.println("Categories:");
        articleDTO.getCategories().forEach(c -> System.out.println("  -> " + c.getName()));
        System.out.println("Likes: " + articleDTO.getLikeCount() + " DisLikes: " + articleDTO.getDislikeCount());
        System.out.printf("Content: %s%nSource: %s%nURL: %s%n",
                articleDTO.getContent(), articleDTO.getSource(), articleDTO.getUrl());
    }

    private void reportArticle(ArticleDTO articleDTO) {
        String reason = ConsoleUtils.readLine(REPORT_REASON_PROMPT);

        newsService.reportArticle(session.getUserId(), articleDTO.getArticleId(), reason);
    }

    private void showArticleReadHistory() {
        List<ArticleReadHistoryDTO> articlesReadHistory = newsService.getArticlesReadHistory(session.getUserId());
        if (articlesReadHistory == null || articlesReadHistory.isEmpty()) {
            System.out.println(NO_ARTICLE_HISTORY);
        } else {
            printIndexed(articlesReadHistory, ArticleReadHistoryDTO::getArticleTitle);
        }
    }
}
