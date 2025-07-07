package com.news_aggregation_system.service.user;

import com.news_aggregation_system.dto.UserDTO;
import com.news_aggregation_system.model.Role;
import com.news_aggregation_system.model.User;
import com.news_aggregation_system.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock  private UserRepository userRepository;
    @InjectMocks private UserServiceImpl userService;

    @Test
    @DisplayName("getById – returns DTO when user exists")
    void getById_success() {

        User entity = new User();
        entity.setUserId(1L);
        entity.setEmail("alice@example.com");
        entity.setName("Alice");
        Role r = new Role(); r.setRole("USER");
        entity.setRole(r);

        when(userRepository.findById(1L)).thenReturn(Optional.of(entity));

        UserDTO dto = userService.getById(1L);

        assertThat(dto).isNotNull();
        assertThat(dto.getUserId()).isEqualTo(1L);
        assertThat(dto.getEmail()).isEqualTo("alice@example.com");
        assertThat(dto.getName()).isEqualTo("Alice");
    }

    @Test
    @DisplayName("getById – returns  user not found")
    void getById_notFound() {

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getById(anyLong()));

    }

}