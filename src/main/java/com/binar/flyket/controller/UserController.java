package com.binar.flyket.controller;


import com.binar.flyket.dto.model.UserDTO;
import com.binar.flyket.dto.request.UpdateRequest;
import com.binar.flyket.dto.response.Response;
import com.binar.flyket.dto.response.ResponseError;
import com.binar.flyket.exception.ExceptionType;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.service.UserService;
import com.binar.flyket.utils.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;


@Tag(name = "User Profile")
@CrossOrigin(value = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('BUYER') or hasRole('ADMIN')")
    @PostMapping(value = "/upload-image", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("user-id") String userId) {

        try {

            userService.uploadImage(userId, file);

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

    @PreAuthorize("hasRole('BUYER') or hasRole('ADMIN')")
    @GetMapping("/detail/{user-id}")
    public ResponseEntity<?> getUserById(@PathVariable("user-id") String userId) {
        try {
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(),
                    Constants.SUCCESS_MSG, userService.findById(userId)));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }

    @PreAuthorize("hasRole('BUYER') or hasRole('ADMIN')")
    @PostMapping("/update/{user-id}")
    public ResponseEntity<?> updateUser(@PathVariable("user-id") String userId,
                                        @RequestBody UpdateRequest updateRequest) {
        try {

            userService.updateProfile(userId, updateRequestToDto(updateRequest));

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

    @PreAuthorize("hasRole('BUYER') or hasRole('ADMIN')")
//    @DeleteMapping("/delete/{user-id}")
    public ResponseEntity<?> deleteUserByEmail(@PathVariable("user-id") String userId) {
        try {

            userService.deleteByEmail(userId);

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
