package com.adminclient.client;


import com.adminclient.dto.ArticleDTO;
import com.adminclient.response.ApiResponse;
import com.adminclient.session.UserSession;
import com.adminclient.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class ArticleClient extends BaseClient {

    private static final String BASE = "http://localhost:8080/api/user/articles";
    private final TypeReference<ApiResponse<List<ArticleDTO>>> listType = new TypeReference<>() {};
    // private final TypeReference<ApiResponse<ArticleDTO>> articleType = new TypeReference<>() {};

    public ArticleClient(RestTemplate restTemplate, UserSession session) {
        super(restTemplate, session);
    }

    public List<ArticleDTO> headlinesToday() throws java.io.IOException {
        ResponseEntity<String> res = restTemplate.getForEntity(BASE + "/headlines/today", String.class);
        return JsonUtil.mapper().readValue(res.getBody(), listType).getData();
    }

    public List<ArticleDTO> headlinesByCategory(String category) throws java.io.IOException {
        ResponseEntity<String> res = restTemplate.getForEntity(BASE + "/headlines/category/" + category, String.class);
        return JsonUtil.mapper().readValue(res.getBody(), listType).getData();
    }

    public List<ArticleDTO> search(String query) throws java.io.IOException {
        ResponseEntity<String> res = restTemplate.getForEntity(BASE + "/search?q=" + query, String.class);
        return JsonUtil.mapper().readValue(res.getBody(), listType).getData();
    }

    public void saveArticle(long articleId) {
        restTemplate.postForObject(BASE + "/saved/" + articleId, null, Void.class);
    }

    public void deleteSaved(long articleId) {
        try{

        }catch (Exception exception){
            System.out.println("Delete Failed: "+exception.getMessage());
        }
        restTemplate.delete(BASE + "/saved/" + articleId);
    }

    public List<ArticleDTO> savedArticles() throws java.io.IOException {
        ResponseEntity<String> res = restTemplate.getForEntity(BASE + "/saved", String.class);
        return JsonUtil.mapper().readValue(res.getBody(), listType).getData();
    }
}
