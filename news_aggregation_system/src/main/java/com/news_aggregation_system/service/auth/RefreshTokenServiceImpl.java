package com.news_aggregation_system.service.auth;

import com.news_aggregation_system.model.RefreshToken;
import com.news_aggregation_system.repository.RefreshTokenRepository;
import com.news_aggregation_system.security.JwtUtil;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public void saveOrUpdate(String email, String token, long expiryMs) {
        refreshTokenRepository.findByEmail(email).ifPresentOrElse(refreshToken -> {
            refreshToken.setToken(token);
            refreshToken.setExpiryDate(Instant.now().plusMillis(expiryMs));
            refreshTokenRepository.save(refreshToken);
        }, () -> {
            RefreshToken rt = new RefreshToken();
            rt.setEmail(email);
            rt.setToken(token);
            rt.setExpiryDate(Instant.now().plusMillis(expiryMs));
            refreshTokenRepository.save(rt);
        });
    }

    @Override
    public boolean isValidToken(String email, String token) {
        return refreshTokenRepository.existsByEmailAndTokenAndExpiryDateAfter(email, token, Instant.now());
    }
}
