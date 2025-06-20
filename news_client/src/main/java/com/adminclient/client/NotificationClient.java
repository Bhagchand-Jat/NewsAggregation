package com.adminclient.client;


import com.adminclient.dto.NotificationDTO;
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
import java.util.Map;

public class NotificationClient extends BaseClient {

    private static final String BASE = "http://localhost:8080/api/user/notifications";
    private final TypeReference<ApiResponse<List<NotificationDTO>>> listType = new TypeReference<>() {};

    public NotificationClient(RestTemplate restTemplate, UserSession session) {
        super(restTemplate, session);
    }

    public List<NotificationDTO> findAllUnRead()  {
        try{
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> res = restTemplate.exchange(BASE+"/user/"+session.getUser().getUserId(), HttpMethod.PUT, request, String.class);
        return JsonUtil.mapper().readValue(res.getBody(), listType).getData();
        
        } catch (HttpClientErrorException ex) {
            String msg = ErrorUtil.extractErrorMessage(ex, JsonUtil.mapper());
            System.out.println("Failed To get Notifications: " + msg);
            
        } catch (Exception ex) {
            System.out.println("Failed To get Notifications:" + ex.getMessage());
            
        }
        return new ArrayList<>();
    }

    public void configure(Map<String, Object> config) {
        try{
        restTemplate.postForObject(BASE + "/config", config, Void.class);
        } catch (HttpClientErrorException ex) {
            String msg = ErrorUtil.extractErrorMessage(ex, JsonUtil.mapper());
            System.out.println("Failed To configure Notification: " + msg);
            
        } catch (Exception ex) {
            System.out.println("Failed To configure Notification:" + ex.getMessage());
            
        }
       
    }
}
