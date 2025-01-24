package com.nextstep.users.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StudentDTO extends UserDTO {
    @NotBlank(message = "School is mandatory for students")
    private String school;

    @NotBlank(message = "District is mandatory for students")
    private String district;

    private StudentProfileDTO studentProfile;
}