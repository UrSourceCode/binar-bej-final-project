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
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private BCryptPasswordEncoder encoder;

    private Cloudinary cloudinary;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder encoder, Cloudinary cloudinary) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.cloudinary = cloudinary;
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
                userModel.setImgUrl("");
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

    @Override
    public Boolean uploadImage(String email, MultipartFile file) throws IOException {
        Optional<User> user = userRepository.findByEmail(email);

        if(user.isEmpty())
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.NOT_FOUND_MSG);

        String fileExe = Objects.requireNonNull(file.getContentType()).split("/")[0];

        if(!fileExe.equalsIgnoreCase("image")) {
            throw FlyketException.throwException(ExceptionType.UPLOAD_FAILED, HttpStatus.NOT_ACCEPTABLE, "check file extension, should jpg, png or jpeg");
        }

        File uploadFile = Constants.multipartToFile(file, file.getName());

        Map uploadResult = cloudinary.uploader().upload(uploadFile, ObjectUtils.emptyMap());
        User userModel = user.get();
        userModel.setImgUrl(uploadResult.get("url").toString());
        userRepository.save(userModel);
        return true;
    }
}
