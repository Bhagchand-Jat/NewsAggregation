package com.adminclient.auth;

import com.adminclient.dto.UserDTO;
import com.adminclient.response.ApiResponse;
import com.adminclient.session.UserSession;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AuthServiceClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    
    private final UserSession session;

    public AuthServiceClient(UserSession session) { this.session = session; }

    public boolean login(String email, String password) {
        LoginRequest request = new LoginRequest(email, password);
        try {
            mapper.registerModule(new JavaTimeModule());
            ResponseEntity<String> response = restTemplate.postForEntity(
                    "http://localhost:8080/api/auth/login", request, String.class);
            ApiResponse<UserDTO> apiResponse = mapper.readValue(response.getBody(), new TypeReference<>() {});
            if (apiResponse.isSuccess()) {
                session.login(apiResponse.getData());
                System.out.println("Logged in as " + session.getName());
                return true;
            }else{
              System.out.println( apiResponse.getMessage());
            }
            
        } catch (Exception ex) {
            System.out.println("Invalid Email or Password");
        }
        return false;
    }
}