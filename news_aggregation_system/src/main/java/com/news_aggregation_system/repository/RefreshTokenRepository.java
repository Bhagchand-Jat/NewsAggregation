package com.news_aggregation_system.repository;

import com.news_aggregation_system.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByEmail(String email);
    void deleteByEmail(String email);
    boolean existsByEmailAndTokenAndExpiryDateAfter(String email, String token, Instant expiryMs);
}
