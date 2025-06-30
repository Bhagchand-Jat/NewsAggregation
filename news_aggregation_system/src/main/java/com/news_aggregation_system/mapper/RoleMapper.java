package com.news_aggregation_system.mapper;

import com.news_aggregation_system.dto.RoleDTO;
import com.news_aggregation_system.model.Role;

public class RoleMapper {

    public static RoleDTO toDto(Role role) {
        RoleDTO dto = new RoleDTO();
        dto.setRoleId(role.getRoleId());
        dto.setRole(role.getRole());
        return dto;
    }

    public static Role toEntity(RoleDTO dto) {
        Role role = new Role();
        role.setRole(dto.getRole());
        return role;
    }
}
