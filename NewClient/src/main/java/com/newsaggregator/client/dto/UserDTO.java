package com.newsaggregator.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.newsaggregator.client.model.Role;

@JsonInclude
public class UserDTO {

    private Long userId;


    private String email;

    private String name;

    private String password;

    private Role role;

    public UserDTO() {
    }


    public UserDTO(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


}
