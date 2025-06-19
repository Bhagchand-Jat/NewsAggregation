package com.news_aggregation_system.service.auth;

import com.news_aggregation_system.dto.LoginRequest;
import com.news_aggregation_system.dto.LoginResponse;
import com.news_aggregation_system.dto.UserDTO;
import com.news_aggregation_system.exception.LoginFailedException;
import com.news_aggregation_system.mapper.UserMapper;
import com.news_aggregation_system.model.User;
import com.news_aggregation_system.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDTO login(LoginRequest request) {
        Optional<User> user = userRepository.findByEmail(request.getEmail());

        if (user.isEmpty() || !passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
            throw new LoginFailedException("Invalid email or password");
        }

        return  UserMapper.toDto(user.get());


    }

    @Override
    public void logout(Long userId) {
        throw new UnsupportedOperationException("Unimplemented method 'logout'");
    }


}
