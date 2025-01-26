package com.nextstep.users.service;

import com.nextstep.users.dto.*;
import com.nextstep.users.mapper.UserMapper;
import com.nextstep.users.model.*;
import com.nextstep.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final RestTemplate restTemplate;

    @Value("${next-step-recommendations.url}")
    private String nextStepRecommendationsUrl;

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
        student.setStudentProfile(null);

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

        HttpHeaders headers = new HttpHeaders();
        headers.set("UUID", studentId.toString());

        HttpEntity<StudentProfileDTO> requestEntity = new HttpEntity<>(updatedProfileDTO, headers);

        String predictionServiceUrl = nextStepRecommendationsUrl + "/predictions/career-prediction";
        StudentProfileDTO updatedProfileWithProbabilities = restTemplate.postForObject(predictionServiceUrl, requestEntity, StudentProfileDTO.class);
        assert updatedProfileWithProbabilities != null;

        studentProfile.setEducationLevel(updatedProfileWithProbabilities.getEducationLevel());
        studentProfile.setOlResults(updatedProfileWithProbabilities.getOlResults());
        studentProfile.setAlStream(updatedProfileWithProbabilities.getAlStream());
        studentProfile.setAlResults(updatedProfileWithProbabilities.getAlResults());
        studentProfile.setCareerProbabilities(updatedProfileWithProbabilities.getCareerProbabilities());
        studentProfile.setGpa(updatedProfileWithProbabilities.getGpa());

        userRepository.save(student);

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
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
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
    public String deleteUser(UUID id) {
        return userRepository.findById(id).map(user -> { userRepository.delete(user); return "User deleted successfully"; }).orElse("User not found");
    }

    @Transactional(readOnly = true)
    public UUID authenticate(String username, String password) {
        UserDTO user = getUserByUsername(username);

        if (user.getPassword().equals(password)) {
            return user.getId();
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}