package com.nextstep.users.service;

import com.nextstep.users.dto.UserDTO;
import com.nextstep.users.dto.StudentDTO;
import com.nextstep.users.dto.InstitutionDTO;
import com.nextstep.users.dto.StudentProfileDTO;
import com.nextstep.users.model.StudentProfile;
import com.nextstep.users.model.User;
import com.nextstep.users.model.Student;
import com.nextstep.users.model.Institution;
import com.nextstep.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserDTO createStudent(StudentDTO studentDTO) {
        Student student = new Student();
        student.setUsername(studentDTO.getUsername());
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        student.setPassword(studentDTO.getPassword());
        student.setTelephone(studentDTO.getTelephone());
        student.setRole(studentDTO.getRole());
        student.setSchool(studentDTO.getSchool());
        student.setDistrict(studentDTO.getDistrict());
        student = userRepository.save(student);

        return mapToDTO(student);
    }

    @Transactional
    public UserDTO createInstitution(InstitutionDTO institutionDTO) {
        Institution institution = new Institution();
        institution.setUsername(institutionDTO.getUsername());
        institution.setName(institutionDTO.getName());
        institution.setEmail(institutionDTO.getEmail());
        institution.setPassword(institutionDTO.getPassword());
        institution.setTelephone(institutionDTO.getTelephone());
        institution.setRole(institutionDTO.getRole());
        institution.setAddress(institutionDTO.getAddress());
        institution.setContactPerson(institutionDTO.getContactPerson());
        institution.setInstitutionType(institutionDTO.getInstitutionType());
        institution = userRepository.save(institution);
        return mapToDTO(institution);
    }

    @Transactional(readOnly = true)
    public UserDTO getUserById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return mapToDTO(user);
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Transactional
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public StudentProfile updateStudentProfile(UUID studentId, StudentProfileDTO updatedProfileDTO) {
        Student student = (Student) userRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        StudentProfile studentProfile = student.getStudentProfile();
        if (studentProfile == null) {
            studentProfile = new StudentProfile();
            studentProfile.setId(studentId);
            student.setStudentProfile(studentProfile);
        }
        studentProfile.setEducationLevel(updatedProfileDTO.getEducationLevel());
        studentProfile.setOlResults(updatedProfileDTO.getOlResults());
        studentProfile.setAlStream(updatedProfileDTO.getAlStream());
        studentProfile.setAlResults(updatedProfileDTO.getAlResults());
        studentProfile.setGpa(updatedProfileDTO.getGpa());
        return studentProfile;
    }

    private UserDTO mapToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setTelephone(user.getTelephone());
        userDTO.setRole(user.getRole());
        userDTO.setActive(user.isActive());
        return userDTO;
    }
}