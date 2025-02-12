package com.nextstep.users.mapper;

import com.nextstep.users.dto.InstitutionDTO;
import com.nextstep.users.model.Institution;

@SuppressWarnings("all")
public class InstitutionMapper {

    public InstitutionDTO institutionToInstitutionDTO(Institution institution) {
        if (institution == null) {
            return null;
        }

        InstitutionDTO institutionDTO = new InstitutionDTO();
        institutionDTO.setId(institution.getId());
        institutionDTO.setUsername(institution.getUsername());
        institutionDTO.setName(institution.getName());
        institutionDTO.setEmail(institution.getEmail());
        institutionDTO.setPassword(institution.getPassword());
        institutionDTO.setTelephone(institution.getTelephone());
        institutionDTO.setRole(institution.getRole());
        institutionDTO.setActive(institution.isActive());
        institutionDTO.setCreatedAt(institution.getCreatedAt());
        institutionDTO.setUpdatedAt(institution.getUpdatedAt());
        institutionDTO.setAddress(institution.getAddress());
        institutionDTO.setContactPerson(institution.getContactPerson());
        institutionDTO.setInstitutionType(institution.getInstitutionType());

        return institutionDTO;
    }

    public Institution institutionDTOToInstitution(InstitutionDTO institutionDTO) {
        if (institutionDTO == null) {
            return null;
        }

        Institution institution = new Institution();
        institution.setId(institutionDTO.getId());
        institution.setUsername(institutionDTO.getUsername());
        institution.setName(institutionDTO.getName());
        institution.setEmail(institutionDTO.getEmail());
        institution.setPassword(institutionDTO.getPassword());
        institution.setTelephone(institutionDTO.getTelephone());
        institution.setRole(institutionDTO.getRole());
        institution.setActive(institutionDTO.isActive());
        institution.setCreatedAt(institutionDTO.getCreatedAt());
        institution.setUpdatedAt(institutionDTO.getUpdatedAt());
        institution.setAddress(institutionDTO.getAddress());
        institution.setContactPerson(institutionDTO.getContactPerson());
        institution.setInstitutionType(institutionDTO.getInstitutionType());

        return institution;
    }
}