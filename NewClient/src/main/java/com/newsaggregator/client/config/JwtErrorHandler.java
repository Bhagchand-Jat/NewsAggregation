package com.newsaggregator.client.config;

import com.newsaggregator.client.service.AuthService;
import com.newsaggregator.client.session.TokenHolder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

public class JwtErrorHandler implements ResponseErrorHandler {

    private final AuthService authService;

    public JwtErrorHandler(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean hasError(ClientHttpResponse resp) throws IOException {
        return resp.getStatusCode() == HttpStatus.UNAUTHORIZED;
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        if (tryRefresh()) return;
        throw new HttpClientErrorException(response.getStatusCode(), response.getStatusText());
    }


    private boolean tryRefresh() {
        if (!TokenHolder.hasRefresh()) return false;
        try {
            RestTemplate bare = new RestTemplate();
            Map<String, String> body = Map.of("refreshToken", TokenHolder.getRefreshToken());
            String json = bare.postForObject(Constant.REFRESH_URL, body, String.class);
            JsonNode root = MAPPER.readTree(json);
            String newAccess = root.path("accessToken").asText();
            String newRefresh = root.path("refreshToken").asText();
            if (!newAccess.isBlank()) {
                TokenHolder.setTokens(newAccess,
                        newRefresh.isBlank() ? TokenHolder.getRefreshToken() : newRefresh);
                return true;
            }
        } catch (Exception ignored) {
        }
        TokenHolder.clear();
        return false;
    }
}
