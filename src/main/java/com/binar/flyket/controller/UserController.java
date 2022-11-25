package com.binar.flyket.controller;


import com.binar.flyket.dto.model.UserDTO;
import com.binar.flyket.dto.request.UpdateRequest;
import com.binar.flyket.dto.response.Response;
import com.binar.flyket.dto.response.ResponseError;
import com.binar.flyket.exception.ExceptionType;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.service.UserService;
import com.binar.flyket.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@RequestParam("email") String email,
                                        @RequestBody UpdateRequest updateRequest) {
        try {
            if(!Constants.validateEmail(email))
                throw FlyketException.throwException(ExceptionType.INVALID_EMAIL,
                        HttpStatus.NOT_ACCEPTABLE, Constants.INVALID_EMAIL_MSG);

            userService.updateProfile(email, updateRequestToDto(updateRequest));

            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(),
                    Constants.SUCCESS_MSG, null));
        } catch (FlyketException.EmailValidateException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(),
                    new Date(),e.getMessage()), e.getStatusCode());
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(),
                    new Date(),e.getMessage()), e.getStatusCode());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUserByEmail(@RequestParam("email") String email) {
        try {
            if(!Constants.validateEmail(email))
                throw FlyketException.throwException(ExceptionType.INVALID_EMAIL,
                        HttpStatus.NOT_ACCEPTABLE, Constants.INVALID_EMAIL_MSG);

            userService.deleteByEmail(email);

            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(),
                    Constants.SUCCESS_MSG, null));
        } catch (FlyketException.EmailValidateException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(),
                    new Date(),e.getMessage()), e.getStatusCode());
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(),
                    new Date(),e.getMessage()), e.getStatusCode());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> findUserByEmail(@RequestParam("email") String email) {
        try {
            if(!Constants.validateEmail(email))
                throw FlyketException.throwException(ExceptionType.INVALID_EMAIL,
                        HttpStatus.NOT_ACCEPTABLE, Constants.INVALID_EMAIL_MSG);

            UserDTO userDTO = userService.findByEmail(email);

            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(),
                    Constants.SUCCESS_MSG, userDTO));
        } catch (FlyketException.EmailValidateException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(),
                    new Date(),e.getMessage()), e.getStatusCode());
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(),
                    new Date(),e.getMessage()), e.getStatusCode());
        }
    }

    private UserDTO updateRequestToDto(UpdateRequest updateRequest) {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(updateRequest.getFirstName());
        userDTO.setLastName(updateRequest.getLastName());
        return userDTO;
    }
}