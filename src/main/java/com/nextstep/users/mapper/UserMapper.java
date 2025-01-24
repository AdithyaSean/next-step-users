package com.nextstep.users.mapper;

import com.nextstep.users.dto.*;
import com.nextstep.users.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(User user);
    StudentDTO studentToStudentDTO(Student student);
    InstitutionDTO institutionToInstitutionDTO(Institution institution);
    StudentProfileDTO studentProfileToStudentProfileDTO(StudentProfile studentProfile);
}