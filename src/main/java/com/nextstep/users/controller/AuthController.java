package com.nextstep.users.controller;

import com.nextstep.users.dto.UserDTO;
import com.nextstep.users.model.User;
import com.nextstep.users.model.UserRole;
import com.nextstep.users.service.UserService;
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

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseEntity<UserDTO> getUser(@AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        User user = userService.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(principal.getAttribute("name"));
            newUser.setRole(UserRole.STUDENT); // Default role
            newUser.setPassword(""); // No password for OAuth2 users
            return userService.createUser(newUser);
        });

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        userDTO.setActive(user.isActive());

        return ResponseEntity.ok(userDTO);
    }
}