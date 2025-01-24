package com.nextstep.users.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "institutions")
@Data
@NoArgsConstructor
public class Institution extends User {
    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String contactPerson;

    @Column(nullable = false)
    private String institutionType;
}