package com.news_aggregation_system.service.auth;

import com.news_aggregation_system.dto.LoginRequest;
import com.news_aggregation_system.dto.UserDTO;
import com.news_aggregation_system.exception.NotFoundException;
import com.news_aggregation_system.mapper.UserMapper;
import com.news_aggregation_system.response.JwtAuthResponse;
import com.news_aggregation_system.security.CustomUserDetails;
import com.news_aggregation_system.security.JwtUtil;
import com.news_aggregation_system.service.user.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.news_aggregation_system.service.common.Constant.UNIMPLEMENTED_LOGOUT;

@Transactional
@Service
public class AuthServiceImpl implements AuthService {


    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenService refreshTokenService;

    public AuthServiceImpl( UserService userService, AuthenticationManager authManager, JwtUtil jwtUtil, UserDetailsService userDetailsService, RefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public JwtAuthResponse login(LoginRequest request) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String accessToken  = jwtUtil.generateAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);
        refreshTokenService.saveOrUpdate(request.getEmail(), refreshToken, 7*24*60*60*1000);
        return new JwtAuthResponse(accessToken, refreshToken);


    }

    @Transactional
    @Override
    public JwtAuthResponse register(UserDTO user) {

          UserDTO userDTO=  userService.create(user);
        UserDetails userDetails = new CustomUserDetails(UserMapper.toEntity(userDTO));
        String accessToken  = jwtUtil.generateAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);
        refreshTokenService.saveOrUpdate(userDTO.getEmail(), refreshToken, 7*24*60*60*1000);
        return new JwtAuthResponse(accessToken, refreshToken);

    }

    @Override
    public void logout(Long userId) {
        throw new UnsupportedOperationException(UNIMPLEMENTED_LOGOUT);
    }

    @Override
    public JwtAuthResponse refresh(String refreshToken) {
        String email = jwtUtil.extractUsername(refreshToken);
        if (!refreshTokenService.isValidToken(email, refreshToken)) throw new NotFoundException("Invalid refresh token");
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String access = jwtUtil.generateAccessToken(userDetails);
        return new JwtAuthResponse(access, refreshToken);
    }


}
