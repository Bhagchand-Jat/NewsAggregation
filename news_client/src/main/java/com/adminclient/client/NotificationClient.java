package com.adminclient.client;


import com.adminclient.dto.NotificationDTO;
import com.adminclient.response.ApiResponse;
import com.adminclient.session.UserSession;
import com.adminclient.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class NotificationClient extends BaseClient {

    private static final String BASE = "http://localhost:8080/api/user/notifications";
    private final TypeReference<ApiResponse<List<NotificationDTO>>> listType = new TypeReference<>() {};

    public NotificationClient(RestTemplate restTemplate, UserSession session) {
        super(restTemplate, session);
    }

    public List<NotificationDTO> findAll() throws java.io.IOException {
        ResponseEntity<String> res = restTemplate.getForEntity(BASE, String.class);
        return JsonUtil.mapper().readValue(res.getBody(), listType).getData();
    }

    public void configure(Map<String, Object> config) {
        restTemplate.postForObject(BASE + "/config", config, Void.class);
    }
}
