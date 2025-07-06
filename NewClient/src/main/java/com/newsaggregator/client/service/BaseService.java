package com.newsaggregator.client.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.newsaggregator.client.response.ApiResponse;
import com.newsaggregator.client.util.HttpUtil;
import com.newsaggregator.client.util.UiText;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class BaseService {

    protected <T> T safeGet(String url, TypeReference<T> type) {
        try {
            ApiResponse<T> response = HttpUtil.getForDto(url, type);
            if (response.isSuccess()) return response.getData();
            System.out.println("Error: " + response.getMessage());
        } catch (HttpStatusCodeException exception) {
            handleError(exception);
        } catch (Exception exception) {
            System.out.println("\n" + UiText.SOMETHING_WENT_WRONG + "\n");
        }
        return null;
    }

    protected <T> T safePost(String url, Object body, TypeReference<T> type) {
        try {
            ApiResponse<T> response = HttpUtil.postForDto(url, body, type);
            System.out.println(response.getMessage());
            return response.getData();
        } catch (HttpStatusCodeException exception) {
            handleError(exception);
        } catch (Exception exception) {
            System.out.println("\n" + UiText.SOMETHING_WENT_WRONG + "\n");
        }
        return null;
    }

    protected <T> List<T> safePostList(String url, Object body, TypeReference<List<T>> type) {
        try {
            ApiResponse<List<T>> response = HttpUtil.postForDto(url, body, type);
            System.out.println(response.getMessage());
            return response.getData();
        } catch (HttpStatusCodeException exception) {
            handleError(exception);
        } catch (Exception exception) {
            System.out.println("\n" + UiText.SOMETHING_WENT_WRONG + "\n");
        }
        return Collections.emptyList();
    }

    protected <T> List<T> safeGetList(String url, TypeReference<List<T>> type) {
        try {
            ApiResponse<List<T>> response = HttpUtil.getForList(url, type);
            if (response.isSuccess()) return response.getData();
            System.out.println("Error: " + response.getMessage());
        } catch (HttpStatusCodeException exception) {
            handleError(exception);
        } catch (Exception exception) {
            System.out.println("\n" + UiText.SOMETHING_WENT_WRONG + "\n");
        }
        return Collections.emptyList();
    }

    protected <T> List<T> safePatchList(String url, TypeReference<List<T>> type) {
        try {
            ApiResponse<List<T>> response = HttpUtil.patchForList(url, type);
            System.out.println(response.getMessage());
            return response.getData();
        } catch (HttpStatusCodeException exception) {
            handleError(exception);
        } catch (Exception exception) {
            System.out.println("\n" + UiText.SOMETHING_WENT_WRONG + "\n");
        }
        return Collections.emptyList();
    }

    protected <T> T safePatch(String url, TypeReference<T> type) {
        try {
            ApiResponse<T> response = HttpUtil.patchForDto(url, type);
            System.out.println(response.getMessage());
            return response.getData();
        } catch (HttpStatusCodeException exception) {
            handleError(exception);
        } catch (Exception exception) {
            System.out.println("\n" + UiText.SOMETHING_WENT_WRONG + "\n");
        }
        return null;
    }

    protected boolean safeDelete(String url, String successMessage) {
        try {
            HttpUtil.delete(url, null);
            System.out.println(successMessage);
            return true;
        } catch (HttpStatusCodeException exception) {
            handleError(exception);
        } catch (Exception exception) {
            System.out.println("\n" + UiText.SOMETHING_WENT_WRONG + "\n");
        }
        return false;
    }

    protected void handleError(HttpStatusCodeException httpStatusCodeException) {
        ApiResponse<Map<String, String>> error = HttpUtil.extractErrorResponse(httpStatusCodeException);
        System.out.println("\nError: " + error.getMessage());
        if (error.getData() != null) {
            Map<String, String> validationErrorMap = error.getData();
            for (Map.Entry<String, String> entry : validationErrorMap.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

        }
    }
}

