package com.adminclient.client;

import com.adminclient.dto.NewsSourceDTO;
import com.adminclient.dto.SourceDTO;
import com.adminclient.response.ApiResponse;
import com.adminclient.session.UserSession;
import com.adminclient.util.ErrorUtil;
import com.adminclient.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class SourceClient extends BaseClient {

    private static final String BASE = "http://localhost:8080/api/admin/news-sources";

    private final TypeReference<ApiResponse<List<SourceDTO>>> listType = new TypeReference<>() {
    };
    private final TypeReference<ApiResponse<SourceDTO>> singleType = new TypeReference<>() {
    };

    public SourceClient(RestTemplate restTemplate, UserSession session) {
        super(restTemplate, session);
    }

    public List<SourceDTO> findAll() {
        try{
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> res = restTemplate.exchange(BASE, HttpMethod.PUT, request, String.class);
        return JsonUtil.mapper().readValue(res.getBody(), listType).getData();
       
    } catch (HttpClientErrorException ex) {
            String msg = ErrorUtil.extractErrorMessage(ex, JsonUtil.mapper());
            System.out.println("Failed To get Sources: " + msg);
            
        } catch (Exception ex) {
            System.out.println("Failed To get sources:" + ex.getMessage());
            
        }
        return new ArrayList<>();
    }

    public SourceDTO findById(long id) throws java.io.IOException {
        try{
        ResponseEntity<String> res = restTemplate.getForEntity(BASE + "/" + id, String.class);
        return JsonUtil.mapper().readValue(res.getBody(), singleType).getData();
        } catch (HttpClientErrorException ex) {
            String msg = ErrorUtil.extractErrorMessage(ex, JsonUtil.mapper());
            System.out.println("source NotFound: " + msg);
            
        } catch (Exception ex) {
            System.out.println("Source NotFound:" + ex.getMessage());
            
        }
        return null;
    }

    public void update(NewsSourceDTO sourceDTO) {
        try{
        restTemplate.put(BASE + "/" + sourceDTO.getSourceId(),
                sourceDTO);
                   } catch (HttpClientErrorException ex) {
            String msg = ErrorUtil.extractErrorMessage(ex, JsonUtil.mapper());
            System.out.println("source Update Failed: " + msg);
            
        } catch (Exception ex) {
            System.out.println("Source update Failed:" + ex.getMessage());
            
        }
    }
}
