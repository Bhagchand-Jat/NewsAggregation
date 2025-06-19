package com.adminclient.session;

import com.adminclient.dto.UserDTO;

public class UserSession {
    private UserDTO currentUser;


    public void login(UserDTO user) {
        this.currentUser = user;
    }

    public void logout() {
        currentUser = null;

    }

    public boolean isLoggedIn() { return currentUser != null; }

    public boolean isAdmin() { return currentUser != null && "ADMIN".equalsIgnoreCase(currentUser.getRole().getRole()); }

    public UserDTO getUser() { return currentUser; }

}
