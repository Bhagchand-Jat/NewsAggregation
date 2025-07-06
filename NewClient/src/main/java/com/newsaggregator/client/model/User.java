package com.newsaggregator.client.model;

public record User(
        String id,
        String name,
        String email,
        Role role) {
}
