package com.nextstep.users.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Student extends User {
    @Column(nullable = false)
    private String school;

    @Column(nullable = false)
    private String district;
}