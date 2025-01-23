package com.nextstep.users.controller;

import com.nextstep.users.dto.UserDTO;
import com.nextstep.users.model.User;
import com.nextstep.users.model.UserRole;
import com.nextstep.users.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseEntity<UserDTO> getUser(@AuthenticationPrincipal OAuth2User principal) {
        logger.info("OAuth2 authentication attempt for principal: {}", principal.getName());
        String email = principal.getAttribute("email");
        logger.debug("Extracted email from OAuth2 principal: {}", email);

        User user = userService.findByEmail(email).orElseGet(() -> {
            logger.info("Creating new user for OAuth2 email: {}", email);
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(principal.getAttribute("name"));
            newUser.setRole(UserRole.STUDENT); // Default role
            newUser.setPassword(""); // No password for OAuth2 users
            logger.debug("New OAuth2 user details: {}", newUser);
            return userService.createUser(newUser);
        });

        logger.info("Retrieved user with ID: {}", user.getId());
        logger.debug("User details: {}", user);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        userDTO.setActive(user.isActive());

        logger.debug("Returning UserDTO: {}", userDTO);
        return ResponseEntity.ok(userDTO);
    }
}
