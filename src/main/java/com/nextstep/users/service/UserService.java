package com.nextstep.users.service;

import com.nextstep.users.dto.CreateUserRequest;
import com.nextstep.users.dto.UserDTO;
import com.nextstep.users.model.User;
import com.nextstep.users.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    @Transactional
    public UserDTO createUser(CreateUserRequest request) {
        logger.info("Starting user creation for email: {}", request.getEmail());
        logger.debug("User creation request details: {}", request);
        
        if (userRepository.existsByEmail(request.getEmail())) {
            logger.error("User creation failed - email already exists: {}", request.getEmail());
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setTelephone(request.getTelephone());
        user.setSchool(request.getSchool());
        user.setDistrict(request.getDistrict());
        user.setRole(request.getRole());

        logger.debug("Created user object: {}", user);
        
        User savedUser = userRepository.save(user);
        logger.info("Successfully created user with ID: {}", savedUser.getId());
        logger.debug("Saved user details: {}", savedUser);
        
        return convertToDTO(savedUser);
    }

    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public UserDTO getUserById(UUID id) {
        return userRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setActive(false);
        userRepository.save(user);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setActive(user.isActive());
        return dto;
    }
}
