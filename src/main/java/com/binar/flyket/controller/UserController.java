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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('BUYER')")
    @PostMapping("/upload-image")
    public ResponseEntity<?> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("email") String email) {

        try {
            if(!Constants.validateEmail(email))
                throw FlyketException.throwException(ExceptionType.INVALID_EMAIL,
                        HttpStatus.NOT_ACCEPTABLE, Constants.INVALID_EMAIL_MSG);

            userService.uploadImage(email, file);

            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(), Constants.SUCCESS_MSG, null));
        } catch (IOException e) {
            return new ResponseEntity<>(new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    new Date(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (FlyketException.EmailValidateException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(),
                    new Date(),e.getMessage()), e.getStatusCode());
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(),
                    new Date(),e.getMessage()), e.getStatusCode());
        } catch (FlyketException.UploadImageException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(),
                    new Date(), e.getMessage()), e.getStatusCode());
        }

    }

    @PreAuthorize("hasRole('BUYER')")
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
        } catch (FlyketException.InputIsEmptyException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(),
                    new Date(),e.getMessage()), e.getStatusCode());
        }
    }

    @PreAuthorize("hasRole('BUYER')")
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

    @PreAuthorize("hasRole('BUYER') or hasRole('ADMIN')")
    @GetMapping("/{email}")
    public ResponseEntity<?> findUserByEmail(@PathVariable("email") String email) {
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
        if(updateRequest.getPhoneNumber() == null ||
                updateRequest.getLastName() == null ||
                updateRequest.getFirstName() == null)  {
            throw FlyketException.throwException(ExceptionType.EMPTY_REQUEST, HttpStatus.NOT_ACCEPTABLE, Constants.EMPTY_MSG);
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(updateRequest.getFirstName());
        userDTO.setLastName(updateRequest.getLastName());
        userDTO.setPhoneNumber(updateRequest.getPhoneNumber());
        return userDTO;
    }
}
