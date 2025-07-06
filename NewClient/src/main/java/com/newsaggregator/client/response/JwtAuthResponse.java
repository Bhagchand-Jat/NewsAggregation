package com.newsaggregator.client.response;

public record JwtAuthResponse(String accessToken, String refreshToken) {
}
