package com.binar.flyket.dummy;

import com.binar.flyket.model.user.User;

import java.util.List;

public class UserDummies {

    public static List<User> users() {
        User u1 = new User();
        u1.setFirstName("Sheila");
        u1.setLastName("Sui");
        u1.setEmail("sheila@gmail.com");

        User u2 = new User();
        u2.setFirstName("Sophie");
        u2.setLastName("Amundsen");
        u2.setEmail("sophie@gmail.com");

        return List.of(u1, u2);
    }
}
