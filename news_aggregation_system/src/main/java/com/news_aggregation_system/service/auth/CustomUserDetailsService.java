package com.news_aggregation_system.service.auth;

import com.news_aggregation_system.exception.NotFoundException;
import com.news_aggregation_system.model.User;
import com.news_aggregation_system.repository.UserRepository;
import com.news_aggregation_system.security.CustomUserDetails;
import com.news_aggregation_system.service.common.Constant;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

/**
 * Loads users for both form login (by email) and JWT (by id).
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    public CustomUserDetailsService(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repo.findByEmail(email)
                   .map(CustomUserDetails::new)
                   .orElseThrow(() -> new NotFoundException(Constant.USER_NOT_FOUND_WITH_EMAIL + email));
    }

    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        return repo.findById(id)
                   .map(CustomUserDetails::new)
                   .orElseThrow(() -> new UsernameNotFoundException("id=" + id));
    }
}
