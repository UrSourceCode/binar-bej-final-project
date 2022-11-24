package com.binar.flyket.controller;

import com.binar.flyket.dto.model.UserDTO;
import com.binar.flyket.dto.request.RegisRequest;
import com.binar.flyket.exception.ExceptionType;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.model.user.ERoles;
import com.binar.flyket.service.UserServiceImpl;
import com.binar.flyket.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private UserServiceImpl userServiceImpl;

    public AuthController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("/signup")
    private ResponseEntity<?> regisUser(@RequestBody RegisRequest regisRequest) {
        try {
            if(!Constants.validateEmail(regisRequest.getEmail()))
                throw FlyketException.throwException(ExceptionType.INVALID_EMAIL,
                        HttpStatus.NOT_ACCEPTABLE, Constants.INVALID_EMAIL_MSG);

        } catch () {

        }
    }

    private UserDTO regisRequestToDto(RegisRequest regisRequest) {
        UserDTO userDTO = new UserDTO();
        userDTO.setTitle(regisRequest.getTitle());
        userDTO.setFirstName(regisRequest.getFirstName());
        userDTO.setLastName(regisRequest.getLastName());
        userDTO.setPassword(regisRequest.getPassword());
        userDTO.setEmail(regisRequest.getEmail());
        userDTO.setPhoneNumber(regisRequest.getPhoneNumber());
        userDTO.setCreatedAt(LocalDateTime.now());

        ERoles userRole = ERoles.getRole(regisRequest.getRoleName());
        String userId = userRole.name().split("_")[1] + "-" + Constants.randomIdentifier(regisRequest.getEmail())[4];
        userDTO.setId(userId);
        userDTO.setRole(userRole);
        return userDTO;
    }
}
