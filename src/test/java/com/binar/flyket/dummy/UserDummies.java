package com.binar.flyket.dummy;

import com.binar.flyket.model.user.User;

import java.time.LocalDateTime;
import java.util.List;

public class UserDummies {

    public static List<User> users() {
        User u1 = new User();
        u1.setId("Admin-1");
        u1.setFirstName("Sheila");
        u1.setLastName("Sui");
        u1.setEmail("sheila@gmail.com");
        u1.setUpdatedAt(LocalDateTime.now());
        u1.setCreatedAt(LocalDateTime.now());
        u1.setTitle("Ms");
        u1.setPhoneNumber("09888828424772");
        u1.setImgUrl("");
        u1.setRoles(RoleDummies.roles());

        User u2 = new User();
        u2.setId("Buyer-1");
        u2.setFirstName("Sophie");
        u2.setLastName("Amundsen");
        u2.setEmail("sophie@gmail.com");
        u2.setPhoneNumber("09888828424772");
        u2.setImgUrl("");
        u2.setTitle("Ms");
        u2.setUpdatedAt(LocalDateTime.now());
        u2.setCreatedAt(LocalDateTime.now());
        u2.setRoles(RoleDummies.roles());

        return List.of(u1, u2);
    }
}
