package com.nextstep.users.dto;

import com.nextstep.users.model.UserRole;
import lombok.Data;
import java.util.UUID;
import java.time.LocalDateTime;

@Data
public class UserDTO {
    private UUID id;
    private String username;
    private String name;
    private String email;
    private String password;
    private String telephone;
    private String school;
    private String district;
    private UserRole role;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}