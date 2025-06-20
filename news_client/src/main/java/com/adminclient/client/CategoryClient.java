package com.adminclient.client;

import com.adminclient.dto.CategoryDTO;
import com.adminclient.response.ApiResponse;
import com.adminclient.session.UserSession;
import com.adminclient.util.ErrorUtil;
import com.adminclient.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class CategoryClient extends BaseClient {

    private static final String BASE = "http://localhost:8080/api/admin/categories";
    private final TypeReference<ApiResponse<List<CategoryDTO>>> listType = new TypeReference<>() {
    };

    public CategoryClient(RestTemplate restTemplate, UserSession session) {
        super(restTemplate, session);
    }

    public List<CategoryDTO> findAll() {
        try {
            ResponseEntity<String> res = restTemplate.getForEntity(BASE, String.class);
            return JsonUtil.mapper().readValue(res.getBody(), listType).getData();
        } catch (HttpClientErrorException ex) {
            String msg = ErrorUtil.extractErrorMessage(ex, JsonUtil.mapper());
            System.out.println("Getting Categories Failed: " + msg);

        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());

        }
        return new ArrayList<>();

    }

    public List<CategoryDTO> findAllEnabled() {
        try {
            ResponseEntity<String> res = restTemplate.getForEntity(BASE+"/enabled", String.class);
            return JsonUtil.mapper().readValue(res.getBody(), listType).getData();
        } catch (HttpClientErrorException ex) {
            String msg = ErrorUtil.extractErrorMessage(ex, JsonUtil.mapper());
            System.out.println("Getting Categories Failed: " + msg);

        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());

        }
        return new ArrayList<>();

    }

    public void addCategory(String name) {
        try {
            restTemplate.postForObject(BASE, new CategoryDTO(name, session.getUser().getUserId()), String.class);
        } catch (HttpClientErrorException ex) {
            String msg = ErrorUtil.extractErrorMessage(ex, JsonUtil.mapper());
            System.out.println("Add Category Failed: " + msg);

        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }

    }
}