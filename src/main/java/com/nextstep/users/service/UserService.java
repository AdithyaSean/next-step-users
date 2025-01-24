package com.nextstep.users.service;

import com.nextstep.users.dto.*;
import com.nextstep.users.mapper.UserMapper;
import com.nextstep.users.model.*;
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
    private final UserMapper userMapper = UserMapper.INSTANCE;

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

        if (studentDTO.getStudentProfile() != null) {
            StudentProfile studentProfile = new StudentProfile();
            studentProfile.setEducationLevel(studentDTO.getStudentProfile().getEducationLevel());
            studentProfile.setOlResults(studentDTO.getStudentProfile().getOlResults());
            studentProfile.setAlStream(studentDTO.getStudentProfile().getAlStream());
            studentProfile.setAlResults(studentDTO.getStudentProfile().getAlResults());
            studentProfile.setCareerProbabilities(studentDTO.getStudentProfile().getCareerProbabilities());
            studentProfile.setGpa(studentDTO.getStudentProfile().getGpa());
            student.setStudentProfile(studentProfile);
        }

        student = userRepository.save(student);
        return userMapper.studentToStudentDTO(student);
    }

    @Transactional
    public StudentProfileDTO updateStudentProfile(UUID studentId, StudentProfileDTO updatedProfileDTO) {
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
        studentProfile.setCareerProbabilities(updatedProfileDTO.getCareerProbabilities());
        studentProfile.setGpa(updatedProfileDTO.getGpa());
        return userMapper.studentProfileToStudentProfileDTO(studentProfile);
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
        institution = userRepository.save(institution);
        return userMapper.institutionToInstitutionDTO(institution);
    }

    @Transactional(readOnly = true)
    public UserDTO getUserById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.userToUserDTO(user);
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> {
                    if (user instanceof Student) {
                        StudentDTO studentDTO = userMapper.studentToStudentDTO((Student) user);
                        studentDTO.setStudentProfile(userMapper.studentProfileToStudentProfileDTO(((Student) user).getStudentProfile()));
                        return studentDTO;
                    } else if (user instanceof Institution) {
                        return userMapper.institutionToInstitutionDTO((Institution) user);
                    } else {
                        return userMapper.userToUserDTO(user);
                    }
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllStudents() {
        return userRepository.findAll().stream()
                .filter(user -> user instanceof Student)
                .map(user -> {
                    StudentDTO studentDTO = userMapper.studentToStudentDTO((Student) user);
                    studentDTO.setStudentProfile(userMapper.studentProfileToStudentProfileDTO(((Student) user).getStudentProfile()));
                    return studentDTO;
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllInstitutions() {
        return userRepository.findAll().stream()
                .filter(user -> user instanceof Institution)
                .map(user -> userMapper.institutionToInstitutionDTO((Institution) user))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StudentProfileDTO getStudentProfile(UUID studentId) {
        Student student = (Student) userRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        return userMapper.studentProfileToStudentProfileDTO(student.getStudentProfile());
    }

    @Transactional
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}