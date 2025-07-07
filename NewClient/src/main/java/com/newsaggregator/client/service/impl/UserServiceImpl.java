package com.newsaggregator.client.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.newsaggregator.client.dto.UserDTO;
import com.newsaggregator.client.service.BaseService;
import com.newsaggregator.client.service.UserService;
import com.newsaggregator.client.util.Constant;

import java.util.Optional;

public class UserServiceImpl extends BaseService implements UserService {
    @Override
    public Optional<UserDTO> getUser() {
        String url = Constant.API_USER;
        UserDTO userDTO = safeGet(url, new TypeReference<>() {
        });
        if (userDTO == null) {
            return Optional.empty();
        } else {
            return Optional.of(userDTO);
        }
    }
}
