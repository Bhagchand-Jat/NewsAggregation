package com.adminclient.session;

import com.adminclient.dto.UserDTO;

public class UserSession {
    private String email;
    private String name;
    private String role;

    public void login(UserDTO user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.role = user.getRole().getRole();
    }

    public void logout() {
        this.email = null;
        this.name = null;
        this.role = null;
    }

    public boolean isLoggedIn() { return email != null; }
    public boolean isAdmin()     { return role != null && role.contains("ADMIN"); }

    public String getName() { return name; }
    public String getRole() { return role; }
}