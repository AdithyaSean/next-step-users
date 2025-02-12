package com.nextstep.users.mapper;

import com.nextstep.users.dto.UserDTO;
import com.nextstep.users.model.User;

@SuppressWarnings("all")
public class UserMapper {

    public UserDTO userToUserDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setTelephone(user.getTelephone());
        userDTO.setRole(user.getRole());
        userDTO.setActive(user.isActive());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());

        return userDTO;
    }
}