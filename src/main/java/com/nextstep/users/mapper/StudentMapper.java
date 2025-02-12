package com.nextstep.users.mapper;

import com.nextstep.users.dto.StudentDTO;
import com.nextstep.users.dto.StudentProfileDTO;
import com.nextstep.users.model.Student;
import com.nextstep.users.model.StudentProfile;

@SuppressWarnings("all")
public class StudentMapper {

    public StudentDTO studentToStudentDTO(Student student) {
        if (student == null) {
            return null;
        }

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setUsername(student.getUsername());
        studentDTO.setName(student.getName());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setPassword(student.getPassword());
        studentDTO.setTelephone(student.getTelephone());
        studentDTO.setRole(student.getRole());
        studentDTO.setActive(student.isActive());
        studentDTO.setCreatedAt(student.getCreatedAt());
        studentDTO.setUpdatedAt(student.getUpdatedAt());
        studentDTO.setSchool(student.getSchool());
        studentDTO.setDistrict(student.getDistrict());
        studentDTO.setStudentProfile(studentProfileToStudentProfileDTO(student.getStudentProfile()));

        return studentDTO;
    }

    public Student studentDTOToStudent(StudentDTO studentDTO) {
        if (studentDTO == null) {
            return null;
        }

        Student student = new Student();
        student.setId(studentDTO.getId());
        student.setUsername(studentDTO.getUsername());
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        student.setPassword(studentDTO.getPassword());
        student.setTelephone(studentDTO.getTelephone());
        student.setRole(studentDTO.getRole());
        student.setActive(studentDTO.isActive());
        student.setCreatedAt(studentDTO.getCreatedAt());
        student.setUpdatedAt(studentDTO.getUpdatedAt());
        student.setSchool(studentDTO.getSchool());
        student.setDistrict(studentDTO.getDistrict());
        student.setStudentProfile(studentProfileDTOToStudentProfile(studentDTO.getStudentProfile()));

        return student;
    }

    public StudentProfileDTO studentProfileToStudentProfileDTO(StudentProfile studentProfile) {
        if (studentProfile == null) {
            return null;
        }

        StudentProfileDTO studentProfileDTO = new StudentProfileDTO();
        studentProfileDTO.setId(studentProfile.getId());
        studentProfileDTO.setEducationLevel(studentProfile.getEducationLevel());
        studentProfileDTO.setOlResults(studentProfile.getOlResults());
        studentProfileDTO.setAlStream(studentProfile.getAlStream());
        studentProfileDTO.setAlResults(studentProfile.getAlResults());
        studentProfileDTO.setCareerProbabilities(studentProfile.getCareerProbabilities());
        studentProfileDTO.setGpa(studentProfile.getGpa());
        studentProfileDTO.setCreatedAt(studentProfile.getCreatedAt());
        studentProfileDTO.setUpdatedAt(studentProfile.getUpdatedAt());

        return studentProfileDTO;
    }

    public StudentProfile studentProfileDTOToStudentProfile(StudentProfileDTO studentProfileDTO) {
        if (studentProfileDTO == null) {
            return null;
        }

        StudentProfile studentProfile = new StudentProfile();
        studentProfile.setId(studentProfileDTO.getId());
        studentProfile.setEducationLevel(studentProfileDTO.getEducationLevel());
        studentProfile.setOlResults(studentProfileDTO.getOlResults());
        studentProfile.setAlStream(studentProfileDTO.getAlStream());
        studentProfile.setAlResults(studentProfileDTO.getAlResults());
        studentProfile.setCareerProbabilities(studentProfileDTO.getCareerProbabilities());
        studentProfile.setGpa(studentProfileDTO.getGpa());
        studentProfile.setCreatedAt(studentProfileDTO.getCreatedAt());
        studentProfile.setUpdatedAt(studentProfileDTO.getUpdatedAt());

        return studentProfile;
    }
}