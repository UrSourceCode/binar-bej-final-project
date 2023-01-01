package com.binar.flyket.controller;

import com.binar.flyket.dto.model.UserDTO;
import com.binar.flyket.dto.request.LoginRequest;
import com.binar.flyket.dto.request.RegisRequest;
import com.binar.flyket.dto.response.JwtResponse;
import com.binar.flyket.dto.response.Response;
import com.binar.flyket.dto.response.ResponseError;
import com.binar.flyket.exception.ExceptionType;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.model.user.ERoles;
import com.binar.flyket.security.UserDetailsImpl;
import com.binar.flyket.service.UserServiceImpl;
import com.binar.flyket.utils.Constants;
import com.binar.flyket.utils.JwtUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Date;

@Tag(name = "Sign-up & Sign-in")
@CrossOrigin(value = "*", maxAge = 3600L)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserServiceImpl userServiceImpl;

    private final AuthenticationManager authManager;

    private final JwtUtil jwtUtil;

    public AuthController(UserServiceImpl userServiceImpl, AuthenticationManager authManager, JwtUtil jwtUtil) {
        this.userServiceImpl = userServiceImpl;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/sign-up")
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
        } catch (FlyketException.PasswordValidateException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(),
                    new Date(), e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody @Valid LoginRequest loginRequest) {
        try {

            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            String accessToken = jwtUtil.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(),
                    Constants.SUCCESS_MSG, new JwtResponse(userDetails.getUserid(), loginRequest.getEmail(), accessToken)));

        } catch (FlyketException.EmailValidateException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(),
                    new Date(), e.getMessage()), e.getStatusCode());
        }
    }

    private UserDTO regisRequestToDto(RegisRequest regisRequest) {
        if(!Constants.validateEmail(regisRequest.getEmail()))
            throw FlyketException.throwException(ExceptionType.INVALID_EMAIL, HttpStatus.NOT_ACCEPTABLE, Constants.INVALID_EMAIL_MSG);

        if(!Constants.validatePassword(regisRequest.getPassword()))
            throw FlyketException.throwException(ExceptionType.INVALID_PASSWORD, HttpStatus.NOT_ACCEPTABLE, Constants.INVALID_PASSWORD_MSG);

        UserDTO userDTO = new UserDTO();
        userDTO.setTitle(regisRequest.getTitle());
        userDTO.setFirstName(regisRequest.getFirstName().trim());
        userDTO.setLastName(regisRequest.getLastName().trim());
        userDTO.setPassword(regisRequest.getPassword().trim());
        userDTO.setEmail(regisRequest.getEmail().trim());
        userDTO.setPhoneNumber(regisRequest.getPhoneNumber().trim());
        userDTO.setCreatedAt(LocalDateTime.now());

        ERoles userRole = regisRequest.getRoleName() == null ? ERoles.ROLE_BUYER
                : ERoles.getRole(regisRequest.getRoleName());

        String userId = userRole.name().split("_")[1] + "-" + Constants.randomIdentifier(regisRequest.getEmail())[4];
        userDTO.setId(userId);
        userDTO.setRole(userRole);
        return userDTO;
    }
}
