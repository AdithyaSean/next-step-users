package com.nextstep.users.model;

import com.nextstep.users.model.CareerPrediction;

import jakarta.persistence.*;

import java.util.List;
import java.util.Map;

@Entity
public class Student extends User {
    private String contact;
    private String school;
    private String district;

    @ElementCollection
    private Map<String, Double> olResults;  // Updated to Map<String, Double> to match predictor.py

    @ElementCollection
    private Map<String, Double> alResults;  // Updated to Map<String, Double> to match predictor.py

    private Integer stream;  // Updated to Integer to match AL_STREAMS in config.py

    private double zScore;
    private double gpa;

    @ElementCollection
    private List<String> interests;

    @ElementCollection
    private List<String> skills;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CareerPrediction> predictions;

    private String firebaseUid;
    private String firebaseToken;

    // Getters and Setters
}
