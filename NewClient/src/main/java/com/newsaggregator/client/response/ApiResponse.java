package com.newsaggregator.client.response;

import java.time.LocalDateTime;

public class ApiResponse<T> {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final String message;
    private final boolean success;
    private final T data;


    public ApiResponse() {
        this.message = null;
        this.data = null;
        this.success = false;
    }

    public ApiResponse(String message, boolean success, T data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }


    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>("Success", true, data);
    }

    public static <T> ApiResponse<T> ok(String message, T data) {
        return new ApiResponse<>(message, true, data);
    }

    public static ApiResponse<Void> ok() {
        return new ApiResponse<>("Success", true, null);
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

    public LocalDateTime getTimestamp() {
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

    @Override
    public String toString() {
        return "ApiResponse [timestamp=" + timestamp + ", message=" + message + ", success=" + success + ", data="
                + data + "]";
    }


}

