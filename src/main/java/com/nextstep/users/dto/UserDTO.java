package com.nextstep.users.dto;

import com.nextstep.users.model.UserRole;
import lombok.Data;
import java.util.UUID;

@Data
public class UserDTO {
    private UUID id;
    private String name;
    private String email;
    private UserRole role;
    private boolean active;
}
