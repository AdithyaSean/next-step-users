package com.nextstep.users.dto;

import com.nextstep.users.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateUserRequest {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Telephone is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Telephone must be 10 digits")
    private String telephone;

    @NotBlank(message = "School is required")
    private String school;

    @NotBlank(message = "District is required")
    private String district;

    @NotNull(message = "Role is required")
    private UserRole role;
}
