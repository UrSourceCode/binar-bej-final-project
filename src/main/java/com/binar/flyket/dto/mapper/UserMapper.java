package com.binar.flyket.dto.mapper;

import com.binar.flyket.dto.model.UserDTO;
import com.binar.flyket.model.user.ERoles;
import com.binar.flyket.model.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static UserDTO toDto(User userModel) {
        UserDTO userDTO = new UserDTO();
        userDTO.setTitle(userModel.getTitle());
        userDTO.setId(userModel.getId());
        userDTO.setPhoneNumber(userModel.getPhoneNumber());
        userDTO.setLastName(userModel.getLastName());
        userDTO.setImgUrl(userModel.getImgUrl());
        userDTO.setFirstName(userModel.getFirstName());
        userDTO.setEmail(userModel.getEmail());
        userDTO.setCreatedAt(userModel.getCreatedAt());
        userDTO.setUpdatedAt(userModel.getUpdatedAt());

        ERoles role = ERoles.getRole(userModel.getRoles().get(0).toString());
        userDTO.setRole(role);
        return userDTO;
    }
}
