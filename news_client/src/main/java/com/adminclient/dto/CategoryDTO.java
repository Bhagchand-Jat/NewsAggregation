package com.adminclient.dto;

public record CategoryDTO(
        long    categoryId,
        String  name,
        Long    userId
) {}
