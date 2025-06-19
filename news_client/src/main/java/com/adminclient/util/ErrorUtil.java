package com.adminclient.util;

import java.util.Map;

import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ErrorUtil {

    public static String extractErrorMessage(HttpClientErrorException ex, ObjectMapper mapper) {
        try {
            String json = ex.getResponseBodyAsString();
            Map<String, Object> error = mapper.readValue(json, new TypeReference<>() {});
            return (String) error.getOrDefault("message", "Unknown error");
        } catch (Exception parseEx) {
            return "HTTP " + ex.getStatusCode().value() + " " + ex.getStatusText();
        }
    }
}
