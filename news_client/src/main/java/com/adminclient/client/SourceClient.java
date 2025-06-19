package com.adminclient.client;

import com.adminclient.dto.SourceDTO;
import com.adminclient.response.ApiResponse;
import com.adminclient.session.UserSession;
import com.adminclient.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class SourceClient extends BaseClient {

    private static final String BASE = "http://localhost:8080/api/admin/news-sources";

    private final TypeReference<ApiResponse<List<SourceDTO>>> listType = new TypeReference<>() {
    };
    private final TypeReference<ApiResponse<SourceDTO>> singleType = new TypeReference<>() {
    };

    public SourceClient(RestTemplate restTemplate, UserSession session) {
        super(restTemplate, session);
    }

    public List<SourceDTO> findAll() throws java.io.IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> res = restTemplate.exchange(BASE, HttpMethod.PUT, request, String.class);
        return JsonUtil.mapper().readValue(res.getBody(), listType).getData();
    }

    public SourceDTO findById(long id) throws java.io.IOException {
        ResponseEntity<String> res = restTemplate.getForEntity(BASE + "/" + id, String.class);
        return JsonUtil.mapper().readValue(res.getBody(), singleType).getData();
    }

    public void update(long id, String url, boolean active) {
        restTemplate.put(BASE + "/" + id,
                Map.of("sourceUrl", url, "active", active));
    }
}
