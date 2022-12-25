package com.binar.flyket.service;

import com.binar.flyket.dto.mapper.UserMapper;
import com.binar.flyket.dto.model.UserDTO;
import com.binar.flyket.dummy.RoleDummies;
import com.binar.flyket.dummy.UserDummies;
import com.binar.flyket.repository.RoleRepository;
import com.binar.flyket.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

class UserServiceImplTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindById() {
        String userId = "Admin-1";

        Mockito.when(userRepository.findById(userId))
                .thenReturn(Optional.of(UserDummies.users().get(0)));

        var actualValue = userService.findById(userId);
        var expectedFirstName = "Sheila";
        var expectedLastName = "Sui";
        var expectedEmail = "sheila@gmail.com";
        var expectedPhoneNumber = "09888828424772";
        var expectedTitle = "Ms";

        Assertions.assertEquals(expectedEmail, actualValue.getEmail());
        Assertions.assertEquals(userId, actualValue.getId());
        Assertions.assertEquals(expectedFirstName, actualValue.getFirstName());
        Assertions.assertEquals(expectedLastName, actualValue.getLastName());
        Assertions.assertEquals(expectedPhoneNumber, actualValue.getPhoneNumber());
        Assertions.assertEquals(expectedTitle, actualValue.getTitle());
    }

    @Test
    void testFindByEmail() {
        String email = "sheila@gmail.com";

        Mockito.when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(UserDummies.users().get(0)));

        var actualValue = userService.findByEmail(email);
        var expectedFirstName = "Sheila";
        var expectedLastName = "Sui";
        var expectedEmail = "sheila@gmail.com";
        var expectedPhoneNumber = "09888828424772";
        var expectedTitle = "Ms";

        Assertions.assertEquals(expectedEmail, actualValue.getEmail());
        Assertions.assertEquals("Admin-1", actualValue.getId());
        Assertions.assertEquals(expectedFirstName, actualValue.getFirstName());
        Assertions.assertEquals(expectedLastName, actualValue.getLastName());
        Assertions.assertEquals(expectedPhoneNumber, actualValue.getPhoneNumber());
        Assertions.assertEquals(expectedTitle, actualValue.getTitle());
    }

    @Test
    void testRegister() {
        UserDTO usrDto = UserMapper.toDto(UserDummies.users().get(0));
        usrDto.setPassword("Hello World");

        BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
        passEncoder.encode(usrDto.getPassword());

        Mockito.when(userRepository.findByEmail(usrDto.getEmail()))
                .thenReturn(Optional.empty());
        Mockito.when(roleRepository.findByName(usrDto.getRole()))
                .thenReturn(Optional.of(RoleDummies.roles().get(0)));
        Mockito.when(encoder.encode(usrDto.getPassword()))
                        .thenReturn(passEncoder.toString());
        Mockito.when(userRepository.save(UserDummies.users().get(0)))
                .thenReturn(UserDummies.users().get(0));

        var actualValue = userService.register(usrDto);
        var expectedFirstName = "Sheila";
        var expectedLastName = "Sui";
        var expectedEmail = "sheila@gmail.com";
        var expectedPhoneNumber = "09888828424772";
        var expectedTitle = "Ms";

        Assertions.assertEquals(expectedEmail, actualValue.getEmail());
        Assertions.assertEquals(expectedFirstName, actualValue.getFirstName());
        Assertions.assertEquals(expectedLastName, actualValue.getLastName());
        Assertions.assertEquals(expectedTitle, actualValue.getTitle());
        Assertions.assertEquals(expectedPhoneNumber, actualValue.getPhoneNumber());
    }

    @Test
    void testUpdateProfile() {
        String userId = "Admin-1";

        UserDTO usrDto = UserMapper.toDto(UserDummies.users().get(0));
        usrDto.setFirstName("new First Name");
        usrDto.setLastName("new Last Name");
        usrDto.setEmail("newEmail@gmail.com");

        Mockito.when(userRepository.findById(userId))
                .thenReturn(Optional.of(UserDummies.users().get(0)));
        Mockito.when(userRepository.save(UserDummies.users().get(0)))
                .thenReturn(UserDummies.users().get(0));

        var actualValue = userService.updateProfile(userId, usrDto);
        var expectedFirstName = usrDto.getFirstName();
        var expectedLastName = usrDto.getLastName();
        var expectedEmail = usrDto.getEmail();

        Assertions.assertEquals(userId, actualValue.getId());
        Assertions.assertEquals(expectedEmail, actualValue.getEmail());
        Assertions.assertEquals(expectedFirstName, actualValue.getFirstName());
        Assertions.assertEquals(expectedLastName, actualValue.getLastName());
    }

}