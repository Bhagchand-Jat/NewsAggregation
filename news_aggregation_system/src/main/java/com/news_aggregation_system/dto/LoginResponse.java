package com.news_aggregation_system.dto;

public class LoginResponse {
    private String message;
    private boolean success;
    private UserDTO user;

    public LoginResponse() {
    }

    public LoginResponse(String message, boolean success, UserDTO user) {
        this.message = message;
        this.success = success;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
