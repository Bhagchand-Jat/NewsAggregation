package com.news_aggregation_system.service.user;

import com.news_aggregation_system.dto.UserDTO;
import com.news_aggregation_system.exception.AlreadyExistsException;
import com.news_aggregation_system.exception.NotFoundException;
import com.news_aggregation_system.mapper.UserMapper;
import com.news_aggregation_system.model.NotificationConfig;
import com.news_aggregation_system.model.Role;
import com.news_aggregation_system.model.User;
import com.news_aggregation_system.repository.RoleRepository;
import com.news_aggregation_system.repository.UserRepository;
import com.news_aggregation_system.service.common.Constant;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        validateEmailUniqueness(userDTO.getEmail());

        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encodedPassword);
        Role role = getRoleByRoleNameOrThrow(Constant.USER_ROLE);
        userDTO.setRole(role);
        User user = UserMapper.toEntity(userDTO);
        NotificationConfig config = new NotificationConfig();
        config.setUser(user);
        user.setNotificationConfig(config);
        User savedUser = userRepository.save(user);

        return UserMapper.toDto(savedUser);
    }

    @Override
    public UserDTO update(Long id, UserDTO userDTO) {
        User user = getUserOrThrow(id);

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());

        if (isNotEmpty(userDTO.getPassword())) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        User updatedUser = userRepository.save(user);
        return UserMapper.toDto(updatedUser);
    }

    @Override
    public UserDTO getById(Long id) {
        User user = getUserOrThrow(id);
        return UserMapper.toDto(user);
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User", "id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User", "email: " + email));
        return UserMapper.toDto(user);
    }

    private void validateEmailUniqueness(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new AlreadyExistsException("User", "Email: " + email);
        }
    }

    private User getUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User", "id: " + id));
    }

    private Role getRoleByRoleNameOrThrow(String role) {
        return roleRepository.findByRole(role)
                .orElseThrow(() -> new NotFoundException("Role", "name: " + role));
    }

    private boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
