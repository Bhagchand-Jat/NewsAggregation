package com.adminclient.client;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.adminclient.dto.CategoryDTO;
import com.adminclient.dto.SourceDTO;
import com.adminclient.response.ApiResponse;
import com.adminclient.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;

public class SourceClient {

    private static final String BASE = "http://localhost:8080/api/admin/news-sources";
    private final RestTemplate rest;
    private final TypeReference<ApiResponse<List<SourceDTO>>> listType =
            new TypeReference<>() {};
    private final TypeReference<ApiResponse<SourceDTO>> singleType =
            new TypeReference<>() {};

    public SourceClient(RestTemplate rest) { this.rest = rest; }

    public List<SourceDTO> findAll() throws JsonMappingException, JsonProcessingException {
        ResponseEntity<String> res = rest.getForEntity(BASE, String.class);
        return JsonUtil.mapper().readValue(res.getBody(), listType).getData();
    }

    public SourceDTO findById(long id) throws JsonMappingException, JsonProcessingException {
        ResponseEntity<String> res = rest.getForEntity(BASE + "/" + id, String.class);
        return JsonUtil.mapper().readValue(res.getBody(), singleType).getData();
    }

    public void update(long id, String name, String url, boolean active) {
        rest.put(BASE + "/" + id,
                 java.util.Map.of("name", name, "url", url, "active", active));
    }
}