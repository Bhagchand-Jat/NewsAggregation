package com.newsaggregator.client.session;

import com.newsaggregator.client.dto.UserDTO;

public class UserSession {

    private UserDTO user;

    public UserSession() {

    }

    public UserSession(UserDTO user) {
        this.user = user;
    }

    public Long getUserId() {
        return user.getUserId();
    }

    public String getUserName() {
        return user.getName();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public boolean isAdmin() {
        return user.getRole().getRole().equals("ADMIN");
    }


}
