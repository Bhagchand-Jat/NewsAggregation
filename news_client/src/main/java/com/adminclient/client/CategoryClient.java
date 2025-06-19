package com.adminclient.client;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.adminclient.dto.CategoryDTO;
import com.adminclient.response.ApiResponse;
import com.adminclient.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;

public class CategoryClient {
    private static final String BASE = "http://localhost:8080/api/admin/categories";
    private final RestTemplate rest;
    private final TypeReference<ApiResponse<List<CategoryDTO>>> listType =
            new TypeReference<>() {};

    public CategoryClient(RestTemplate rest) { this.rest = rest; }

    public List<CategoryDTO> findAll() throws JsonMappingException, JsonProcessingException {
        ResponseEntity<String> res = rest.getForEntity(BASE, String.class);
        return JsonUtil.mapper().readValue(res.getBody(), listType).getData();
    }

    public void add(String name) {
        rest.postForObject(BASE, Map.of("name", name), Void.class);
    }
}
