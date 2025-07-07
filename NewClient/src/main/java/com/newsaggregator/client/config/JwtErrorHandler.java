package com.newsaggregator.client.config;

import com.newsaggregator.client.response.JwtAuthResponse;
import com.newsaggregator.client.service.AuthService;
import com.newsaggregator.client.session.TokenHolder;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;

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
           Optional< JwtAuthResponse> response=authService.refreshToken(TokenHolder.getRefreshToken());
           if(response.isPresent()){
               JwtAuthResponse jwtAuthResponse=response.get();
               TokenHolder.setTokens(jwtAuthResponse.accessToken(),
                       jwtAuthResponse.refreshToken());
               return true;
           }

        } catch (Exception ignored) {
        }
        return false;
    }
}
