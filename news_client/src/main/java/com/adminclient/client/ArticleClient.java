package com.adminclient.client;


import com.adminclient.dto.ArticleDTO;
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

public class ArticleClient extends BaseClient {

    private static final String BASE = "http://localhost:8080/api/user/articles";
    private final TypeReference<ApiResponse<List<ArticleDTO>>> listType = new TypeReference<>() {};
    // private final TypeReference<ApiResponse<ArticleDTO>> articleType = new TypeReference<>() {};

    public ArticleClient(RestTemplate restTemplate, UserSession session) {
        super(restTemplate, session);
    }

    public List<ArticleDTO> headlinesToday() {
        try{
        ResponseEntity<String> res = restTemplate.getForEntity(BASE + "/headlines/today", String.class);
        return JsonUtil.mapper().readValue(res.getBody(), listType).getData();

        } catch (HttpClientErrorException ex) {
            String msg = ErrorUtil.extractErrorMessage(ex, JsonUtil.mapper());
            System.out.println("Failed To get headlines: " + msg);
            
        } catch (Exception ex) {
            System.out.println("Failed To get headlines:" + ex.getMessage());
            
        }
        return new ArrayList<>();
    }

    public List<ArticleDTO> headlinesByCategory(String category) throws java.io.IOException {
        try{
        ResponseEntity<String> res = restTemplate.getForEntity(BASE + "/headlines/category/" + category, String.class);
        return JsonUtil.mapper().readValue(res.getBody(), listType).getData();
        } catch (HttpClientErrorException ex) {
            String msg = ErrorUtil.extractErrorMessage(ex, JsonUtil.mapper());
            System.out.println("Failed To get headlines: " + msg);
            
        } catch (Exception ex) {
            System.out.println("Failed To get headlines:" + ex.getMessage());
            
        }
        return new ArrayList<>();
    }

    public List<ArticleDTO> search(String query) throws java.io.IOException {
        try{
        ResponseEntity<String> res = restTemplate.getForEntity(BASE + "/search?q=" + query, String.class);
        return JsonUtil.mapper().readValue(res.getBody(), listType).getData();

        } catch (HttpClientErrorException ex) {
            String msg = ErrorUtil.extractErrorMessage(ex, JsonUtil.mapper());
            System.out.println("Failed To get headlines: " + msg);
            
        } catch (Exception ex) {
            System.out.println("Failed To get headlines:" + ex.getMessage());
            
        }
        return new ArrayList<>();
    }

    public void saveArticle(long articleId) {
        try{
        restTemplate.postForObject(BASE + "/saved/" + articleId, null, Void.class);
        } catch (HttpClientErrorException ex) {
            String msg = ErrorUtil.extractErrorMessage(ex, JsonUtil.mapper());
            System.out.println("Failed To Save Article: " + msg);
            
        } catch (Exception ex) {
            System.out.println("Failed To Save Article:" + ex.getMessage());
            
        }
       
    }

    public void deleteSaved(long articleId) {
        try{
          restTemplate.delete(BASE + "/saved/" + articleId);
        } catch (HttpClientErrorException ex) {
            String msg = ErrorUtil.extractErrorMessage(ex, JsonUtil.mapper());
            System.out.println("Delete Saved Article Failed: " + msg);
            
        } catch (Exception ex) {
            System.out.println("Delete Saved Article Failed:" + ex.getMessage());
            
        }
        
        
    }

    public List<ArticleDTO> savedArticles() throws java.io.IOException {
        try{
        ResponseEntity<String> res = restTemplate.getForEntity(BASE + "/saved", String.class);
        return JsonUtil.mapper().readValue(res.getBody(), listType).getData();
        } catch (HttpClientErrorException ex) {
            String msg = ErrorUtil.extractErrorMessage(ex, JsonUtil.mapper());
            System.out.println("Failed To get Saved Articles: " + msg);
            
        } catch (Exception ex) {
            System.out.println("Failed To get Saved Articles:" + ex.getMessage());
            
        }
        return new ArrayList<>();
    }
}
