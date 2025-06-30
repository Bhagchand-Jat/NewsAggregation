package com.news_aggregation_system.dto;

import jakarta.validation.constraints.NotBlank;

public class RoleDTO {
    private Long roleId;

    @NotBlank(message = "Role name is required")
    private String role;

    public RoleDTO() {
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
