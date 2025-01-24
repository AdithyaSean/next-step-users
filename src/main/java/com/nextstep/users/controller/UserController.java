package com.nextstep.users.controller;

import com.nextstep.users.dto.UserDTO;
import com.nextstep.users.dto.StudentDTO;
import com.nextstep.users.dto.InstitutionDTO;
import com.nextstep.users.model.StudentProfile;
import com.nextstep.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users/students")
    public ResponseEntity<UserDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        return new ResponseEntity<>(userService.createUser(studentDTO), HttpStatus.CREATED);
    }

    @PostMapping("/users/institutions")
    public ResponseEntity<UserDTO> createInstitution(@Valid @RequestBody InstitutionDTO institutionDTO) {
        return new ResponseEntity<>(userService.createUser(institutionDTO), HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/students/{id}/profile")
    public ResponseEntity<StudentProfile> updateStudentProfile(@PathVariable UUID id, @Valid @RequestBody StudentProfile studentProfile) {
        return ResponseEntity.ok(userService.updateStudentProfile(id, studentProfile));
    }
}