package com.adminclient.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class ApiResponse<T> {

    private String message;
    private boolean success;
    private T data;
    private LocalDateTime timestamp;

    @JsonCreator
    public ApiResponse(@JsonProperty("message")   String message,
                       @JsonProperty("success")   boolean success,
                       @JsonProperty("data")      T data,
                       @JsonProperty("timestamp") LocalDateTime timestamp) {
        this.message   = message;
        this.success   = success;
        this.data      = data;
        this.timestamp = timestamp;
    }


    public String getMessage()            { return message; }
    public boolean isSuccess()            { return success; }
    public T getData()                    { return data; }
    public LocalDateTime getTimestamp()   { return timestamp; }
}
