package com.adminclient.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

/** Generic wrapper matching server response format. */
public class ApiResponse<T> {

    private final String message;
    private final boolean success;
    private final T data;
    private final LocalDateTime timestamp;

    @JsonCreator
    public ApiResponse(@JsonProperty("message") String message,
                       @JsonProperty("success") boolean success,
                       @JsonProperty("data") T data,
                       @JsonProperty("timestamp") LocalDateTime timestamp) {
        this.message = message;
        this.success = success;
        this.data = data;
        this.timestamp = timestamp;
    }

    public String getMessage() { return message; }
    public boolean isSuccess() { return success; }
    public T getData() { return data; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
