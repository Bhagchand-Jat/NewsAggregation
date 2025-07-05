package com.news_aggregation_system.service.user;

import com.news_aggregation_system.dto.UserDTO;
import com.news_aggregation_system.exception.AlreadyExistsException;
import com.news_aggregation_system.exception.NotFoundException;
import com.news_aggregation_system.mapper.UserMapper;
import com.news_aggregation_system.model.Role;
import com.news_aggregation_system.model.User;
import com.news_aggregation_system.repository.RoleRepository;
import com.news_aggregation_system.repository.UserRepository;
import com.news_aggregation_system.service.common.Constant;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.news_aggregation_system.service.common.Constant.*;

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

    @Transactional
    @Override
    public UserDTO create(UserDTO userDTO) {
        validateEmailUniqueness(userDTO.getEmail());

        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encodedPassword);
        Role role = getRoleByRoleNameOrThrow();
        userDTO.setRole(role);
        User user = UserMapper.toEntity(userDTO);
        User savedUser = userRepository.save(user);

        return UserMapper.toDto(savedUser);
    }

    @Transactional
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

    @Transactional
    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException(USER, ID + id);
        }
        userRepository.deleteById(id);
    }

    private void validateEmailUniqueness(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new AlreadyExistsException(USER, EMAIL + email);
        }
    }

    private User getUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER, ID + id));
    }

    private Role getRoleByRoleNameOrThrow() {
        return roleRepository.findByRole(Constant.USER_ROLE)
                .orElseThrow(() -> new NotFoundException(ROLE, NAME + Constant.USER_ROLE));
    }

    private boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
