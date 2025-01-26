package com.nextstep.users.controller;

import com.nextstep.users.dto.UserDTO;
import com.nextstep.users.dto.StudentDTO;
import com.nextstep.users.dto.InstitutionDTO;
import com.nextstep.users.dto.StudentProfileDTO;
import com.nextstep.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @GetMapping("/students")
    public ResponseEntity<List<UserDTO>> getAllStudents() {
        return ResponseEntity.ok(userService.getAllStudents());
    }

    @PostMapping("/students")
    public ResponseEntity<UserDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        return new ResponseEntity<>(userService.createStudent(studentDTO), HttpStatus.CREATED);
    }

    @GetMapping("/institutions")
    public ResponseEntity<List<UserDTO>> getAllInstitutions() {
        return ResponseEntity.ok(userService.getAllInstitutions());
    }

    @PostMapping("/institutions")
    public ResponseEntity<UserDTO> createInstitution(@Valid @RequestBody InstitutionDTO institutionDTO) {
        return new ResponseEntity<>(userService.createInstitution(institutionDTO), HttpStatus.CREATED);
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<StudentProfileDTO> updateStudentProfile(@PathVariable UUID id, @Valid @RequestBody StudentProfileDTO studentProfileDTO) {
        return ResponseEntity.ok(userService.updateStudentProfile(id, studentProfileDTO));
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<StudentProfileDTO> getStudentProfile(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getStudentProfile(id));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody Map<String, String> credentials) {
        return ResponseEntity.ok(userService.authenticate(credentials.get("username"), credentials.get("password")).toString());
    }
}
