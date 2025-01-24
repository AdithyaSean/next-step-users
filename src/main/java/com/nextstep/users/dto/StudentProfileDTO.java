package com.nextstep.users.dto;

import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class StudentProfileDTO {
    private UUID id;
    private int educationLevel;
    private Map<String, Double> olResults;
    private Integer alStream;
    private Map<String, Double> alResults;
    private Double gpa;
}