package com.news_aggregation_system.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private final Long timestamp = System.currentTimeMillis();
    private final String message;
    private final boolean success;
    private final T data;

    public ApiResponse(String message, boolean success, T data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>("", true, data);
    }

    public static <T> ApiResponse<T> ok(String message, T data) {
        return new ApiResponse<>(message, true, data);
    }

    public static ApiResponse<Void> ok() {
        return new ApiResponse<>("", true, null);
    }

    public static ApiResponse<Void> ok(String message) {
        return new ApiResponse<>(message, true, null);
    }

    public static ApiResponse<Void> error(String message) {
        return new ApiResponse<>(message, false, null);
    }

    public static <T> ApiResponse<T> error(String message, T data) {
        return new ApiResponse<>(message, false, data);
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }
}
