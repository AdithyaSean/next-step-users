package com.nextstep.users.mapper;

import com.nextstep.users.dto.CreateUserRequest;
import com.nextstep.users.dto.UserDTO;
import com.nextstep.users.model.User;
import com.nextstep.users.model.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "school", expression = "java(user instanceof com.nextstep.users.model.Student ? ((com.nextstep.users.model.Student) user).getSchool() : null)")
    @Mapping(target = "district", expression = "java(user instanceof com.nextstep.users.model.Student ? ((com.nextstep.users.model.Student) user).getDistrict() : null)")
    @Mapping(target = "address", expression = "java(user instanceof com.nextstep.users.model.Institution ? ((com.nextstep.users.model.Institution) user).getAddress() : null)")
    @Mapping(target = "contactPerson", expression = "java(user instanceof com.nextstep.users.model.Institution ? ((com.nextstep.users.model.Institution) user).getContactPerson() : null)")
    @Mapping(target = "institutionType", expression = "java(user instanceof com.nextstep.users.model.Institution ? ((com.nextstep.users.model.Institution) user).getInstitutionType() : null)")
    UserDTO toUserDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", constant = "true")
    default User toUser(CreateUserRequest request) {
        User user;
        if (request.getRole() == UserRole.STUDENT) {
            com.nextstep.users.model.Student student = new com.nextstep.users.model.Student();
            student.setSchool(request.getSchool());
            student.setDistrict(request.getDistrict());
            user = student;
        } else if (request.getRole() == UserRole.INSTITUTION) {
            com.nextstep.users.model.Institution institution = new com.nextstep.users.model.Institution();
            institution.setAddress(request.getAddress());
            institution.setContactPerson(request.getContactPerson());
            institution.setInstitutionType(request.getInstitutionType());
            user = institution;
        } else {
            throw new IllegalArgumentException("Invalid role");
        }
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setTelephone(request.getTelephone());
        user.setRole(request.getRole());
        return user;
    }
}