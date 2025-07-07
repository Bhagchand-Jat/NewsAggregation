package com.newsaggregator.client.service;

import com.newsaggregator.client.dto.UserDTO;

import java.util.Optional;

public interface UserService {
    Optional<UserDTO> getUser();
}
