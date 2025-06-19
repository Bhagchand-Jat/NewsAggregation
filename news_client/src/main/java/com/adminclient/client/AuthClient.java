package com.adminclient.client;

import com.adminclient.dto.UserDTO;
import com.adminclient.response.ApiResponse;
import com.adminclient.session.UserSession;
import com.adminclient.util.ErrorUtil;
import com.adminclient.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class AuthClient {

    private static final String BASE = "http://localhost:8080/api/auth";
    private final RestTemplate restTemplate;
    private final UserSession session;

    private final TypeReference<ApiResponse<UserDTO>> userType = new TypeReference<>() {
    };

    public AuthClient(RestTemplate restTemplate, UserSession session) {
        this.restTemplate = restTemplate;
        this.session = session;
    }

    public boolean signup(String name, String email, String password) {
        Map<String, String> body = Map.of("name", name, "email", email, "password", password);
        try {

            ResponseEntity<String> res = restTemplate.postForEntity(BASE + "/signup", body, String.class);

            ApiResponse<UserDTO> api = JsonUtil.mapper().readValue(res.getBody(), userType);
            System.out.println(api.getMessage());
            return api.isSuccess();
        } catch (HttpClientErrorException ex) {
            String msg = ErrorUtil.extractErrorMessage(ex, JsonUtil.mapper());
            System.out.println("Signup failed: " + msg);
            
        } catch (Exception ex) {
            System.out.println("Signup failed: " + ex.getMessage());
            
        }
        return false;
    }

    public boolean login(String email, String password) {
        Map<String, String> body = Map.of("email", email, "password", password);
        try {
            ResponseEntity<String> res = restTemplate.postForEntity(BASE + "/login", body, String.class);

            ApiResponse<UserDTO> api = JsonUtil.mapper().readValue(res.getBody(), userType);
            if (api.isSuccess()) {

                session.login(api.getData());
                return true;
            }

        } catch (HttpClientErrorException ex) {
            String msg = ErrorUtil.extractErrorMessage(ex, JsonUtil.mapper());
            System.out.println("Login failed: " + msg);
            
        } catch (Exception ex) {
            System.out.println("Login failed: " + ex.getMessage());
        }
        return false;
    }
}
