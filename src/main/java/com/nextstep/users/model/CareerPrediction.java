package com.nextstep.users.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Entity
@Data
public class CareerPrediction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String careerName;  // Name of the predicted career (e.g., Engineering, Medicine)
    private double probability;  // Probability of success in the predicted career (0 to 100)
}
