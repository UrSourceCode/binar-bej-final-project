package com.binar.flyket.service;

import com.binar.flyket.dto.mapper.UserMapper;
import com.binar.flyket.dto.model.UserDTO;
import com.binar.flyket.exception.ExceptionType;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.model.user.Roles;
import com.binar.flyket.model.user.User;
import com.binar.flyket.repository.RoleRepository;
import com.binar.flyket.repository.UserRepository;
import com.binar.flyket.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private BCryptPasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDTO findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return UserMapper.toDto(user.get());
        }
        throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND,
                Constants.NOT_FOUND_MSG);
    }

    @Override
    public UserDTO register(UserDTO userDTO) {
        Optional<User> user = userRepository.findByEmail(userDTO.getEmail());
        if (user.isEmpty()) {
            Optional<Roles> role = roleRepository.findByName(userDTO.getRole());
            if(role.isPresent()) {
                User userModel = new User();
                userModel.setId(userDTO.getId());
                userModel.setTitle(userDTO.getTitle());
                userModel.setEmail(userDTO.getEmail());
                userModel.setLastName(userDTO.getLastName());
                userModel.setFirstName(userDTO.getFirstName());
                userModel.setPhoneNumber(userDTO.getPhoneNumber());
                userModel.setPassword(encoder.encode(userDTO.getPassword()));
                userModel.getRoles().add(role.get());
                userModel.setCreatedAt(LocalDateTime.now());
                userModel.setUpdatedAt(LocalDateTime.now());
                userRepository.save(userModel);
                return userDTO;
            }
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND,
                    Constants.ROLE_NOT_FOUND);
        }
        throw FlyketException.throwException(ExceptionType.DUPLICATE_ENTITY, HttpStatus.CONFLICT,
                Constants.ALREADY_EXIST_MSG);
    }
    @Override
    public UserDTO updateProfile(String email, UserDTO userDTO) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()) {
            User userModel = new User();
            userModel.setLastName(userDTO.getLastName());
            userModel.setFirstName(userDTO.getFirstName());
            userModel.setPhoneNumber(userDTO.getPhoneNumber());
            userModel.setUpdatedAt(LocalDateTime.now());
        }
        throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.NOT_FOUND_MSG);
    }
    @Override
    public UserDTO deleteByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()) {
            User userModel = new User();
            userRepository.delete(userModel);
            return UserMapper.toDto(userModel);
        }
        throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND,
                Constants.NOT_FOUND_MSG);
    }
}
