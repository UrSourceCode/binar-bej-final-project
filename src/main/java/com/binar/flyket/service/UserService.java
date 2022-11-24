package com.binar.flyket.service;

import com.binar.flyket.dto.model.UserDTO;

public interface UserService {

    UserDTO findByEmail(String email);

    UserDTO register(UserDTO userDTO);

    UserDTO updateProfile(String email, UserDTO userDTO);

    UserDTO deleteByEmail(String email);
}
