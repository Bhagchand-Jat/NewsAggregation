package com.adminclient.client;

import org.springframework.web.client.RestTemplate;

import com.adminclient.session.UserSession;


public abstract class BaseClient {

    protected final RestTemplate restTemplate;
    protected final UserSession session;

    protected BaseClient(RestTemplate restTemplate, UserSession session) {
        this.restTemplate = restTemplate;
        this.session = session;
    }

    protected Long currentUserId() {
        return session.getUser() != null ? session.getUser().getUserId() : null;
    }
}
