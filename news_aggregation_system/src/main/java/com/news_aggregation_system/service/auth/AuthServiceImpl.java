package com.news_aggregation_system.service.auth;

import com.news_aggregation_system.dto.LoginRequest;
import com.news_aggregation_system.dto.UserDTO;
import com.news_aggregation_system.exception.LoginFailedException;
import com.news_aggregation_system.mapper.UserMapper;
import com.news_aggregation_system.model.User;
import com.news_aggregation_system.repository.UserRepository;
import com.news_aggregation_system.service.user.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.news_aggregation_system.service.common.Constant.INVALID_EMAIL_PASSWORD;
import static com.news_aggregation_system.service.common.Constant.UNIMPLEMENTED_LOGOUT;

@Transactional
@Service
public class AuthServiceImpl implements AuthService {


    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserService userService;


    public AuthServiceImpl(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public UserDTO login(LoginRequest request) {
        Optional<User> user = userRepository.findByEmail(request.getEmail());

        if (user.isEmpty() || !passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
            throw new LoginFailedException(INVALID_EMAIL_PASSWORD);
        }

        return UserMapper.toDto(user.get());


    }

    @Transactional
    @Override
    public UserDTO register(UserDTO user) {
        return userService.create(user);
    }

    @Override
    public void logout(Long userId) {
        throw new UnsupportedOperationException(UNIMPLEMENTED_LOGOUT);
    }


}
