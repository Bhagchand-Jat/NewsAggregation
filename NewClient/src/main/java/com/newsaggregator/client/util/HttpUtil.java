package com.newsaggregator.client.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.newsaggregator.client.config.JwtInterceptor;
import com.newsaggregator.client.response.ApiResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class HttpUtil {
    private static final RestTemplate restTemplate = restTemplate();
    public static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    private HttpUtil() {
    }

    public static RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        factory.setConnectTimeout(5_000);
        factory.setReadTimeout(10_000);

        RestTemplate template = new RestTemplate(factory);

        template.getInterceptors().add(new JwtInterceptor());
        return template;
    }

    private static HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }


    private static <T> ApiResponse<T> parseResponse(ResponseEntity<String> response, TypeReference<T> type) {
        try {
            JsonNode root = MAPPER.readTree(response.getBody());

            String message = root.path("message").asText();
            boolean success = root.path("success").asBoolean();
            JsonNode dataNode = root.path("data");

            T data = dataNode.isMissingNode() || dataNode.isNull()
                    ? null
                    : MAPPER.convertValue(dataNode, type);

            return new ApiResponse<>(message, success, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse response", e);
        }
    }


    public static <T> ApiResponse<T> getForDto(String url, TypeReference<T> type) {

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return parseResponse(response, type);
    }

    public static <T> ApiResponse<List<T>> getForList(String url, TypeReference<List<T>> type) {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return parseResponse(response, type);
    }

    public static <T> ApiResponse<T> postForDto(String url, Object body, TypeReference<T> type) {
        ResponseEntity<String> response = restTemplate.postForEntity(url, body, String.class);
        return parseResponse(response, type);
    }

    public static <T> ApiResponse<T> patchForDto(String url, TypeReference<T> type) {


        HttpEntity<Void> entity = new HttpEntity<>(createHeaders());

        ResponseEntity<String> response =
                restTemplate.exchange(url, HttpMethod.PATCH, entity, String.class);

        return parseResponse(response, type);
    }

    public static <T> ApiResponse<List<T>> patchForList(String url, TypeReference<List<T>> type) {


        HttpEntity<Void> entity = new HttpEntity<>(createHeaders());

        ResponseEntity<String> response =
                restTemplate.exchange(url, HttpMethod.PATCH, entity, String.class);

        return parseResponse(response, type);
    }

    public static void delete(String url, Object body) {
        restTemplate.delete(url, body);
    }


    public static <T> ApiResponse<T> extractErrorResponse(HttpStatusCodeException exception) {
        try {
            JsonNode root = MAPPER.readTree(exception.getResponseBodyAsString());

            String message = root.path("message").asText();
//            boolean success = root.path("success").asBoolean();
            JsonNode dataNode = root.path("data");

            T data = dataNode.isMissingNode() || dataNode.isNull()
                    ? null
                    : MAPPER.convertValue(dataNode, new TypeReference<>() {
            });
            return ApiResponse.error(message, data);
        } catch (Exception parseEx) {
            return new ApiResponse<>(parseEx.getMessage(), false, null);
        }
    }
}
