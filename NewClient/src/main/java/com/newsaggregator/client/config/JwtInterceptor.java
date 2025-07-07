package com.newsaggregator.client.config;

import com.newsaggregator.client.session.TokenHolder;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class JwtInterceptor implements ClientHttpRequestInterceptor {


    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String token = TokenHolder.getAccessToken();
        if (token != null) {
            request.getHeaders().setBearerAuth(token);
        }
        return execution.execute(request, body);
    }
}

