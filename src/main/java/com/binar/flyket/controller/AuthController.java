package com.binar.flyket.controller;

import com.binar.flyket.dto.model.UserDTO;
import com.binar.flyket.dto.request.RegisRequest;
import com.binar.flyket.dto.response.Response;
import com.binar.flyket.dto.response.ResponseError;
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

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private UserServiceImpl userServiceImpl;

    public AuthController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> regisUser(@RequestBody @Valid RegisRequest regisRequest) {
        try {
            if(!Constants.validateEmail(regisRequest.getEmail()))
                throw FlyketException.throwException(ExceptionType.INVALID_EMAIL,
                        HttpStatus.NOT_ACCEPTABLE, Constants.INVALID_EMAIL_MSG);

            userServiceImpl.register(regisRequestToDto(regisRequest));

            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(), Constants.SUCCESS_MSG, null));
        } catch (FlyketException.EmailValidateException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(),
                    new Date(), e.getMessage()), e.getStatusCode());
        } catch (FlyketException.DuplicateEntityException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(),
                    new Date(), e.getMessage()), e.getStatusCode());
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(),
                    new Date(), e.getMessage()), HttpStatus.NOT_FOUND);
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

        ERoles userRole = regisRequest.getRoleName() == null ? ERoles.ROLE_BUYER
                : ERoles.getRole(regisRequest.getRoleName());

        String userId = userRole.name().split("_")[1] + "-" + Constants.randomIdentifier(regisRequest.getEmail())[4];
        userDTO.setId(userId);
        userDTO.setRole(userRole);
        return userDTO;
    }
}
