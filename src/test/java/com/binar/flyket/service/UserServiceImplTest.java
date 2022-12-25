package com.binar.flyket.service;

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

import java.util.Optional;

class UserServiceImplTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

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

    }

    @Test
    void testUpdateProfile() {

    }

    @Test
    void testDeleteByEmail() {

    }


}