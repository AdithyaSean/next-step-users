package com.nextstep.users.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InstitutionDTO extends UserDTO {
    @NotBlank(message = "Address is mandatory for institutions")
    private String address;

    @NotBlank(message = "Contact person is mandatory for institutions")
    private String contactPerson;

    @NotBlank(message = "Institution type is mandatory for institutions")
    private String institutionType;
}