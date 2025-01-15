package com.nextstep.users.model;

import jakarta.persistence.Entity;

@Entity
public class Institution extends User {
    private String address;
    private String contactPerson;
    private String institutionType;

    // Getters and Setters
}
