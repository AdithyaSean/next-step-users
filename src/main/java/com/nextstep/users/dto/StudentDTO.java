package com.nextstep.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class StudentDTO extends UserDTO {
    @NotBlank(message = "School is mandatory for students")
    private String school;

    @NotBlank(message = "District is mandatory for students")
    private String district;

    @NotNull(message = "Education level is mandatory for students")
    private Integer educationLevel;

    private Map<String, Double> olResults;
    private Integer alStream;
    private Map<String, Double> alResults;
    private Double gpa;
}