package com.binar.flyket.dummy;

import com.binar.flyket.model.user.ERoles;
import com.binar.flyket.model.user.Roles;

import java.util.List;

public class RoleDummies {

    public static List<Roles> roles() {
        Roles r1 = new Roles();
        r1.setId(1);
        r1.setName(ERoles.ROLE_ADMIN);

        Roles r2 = new Roles();
        r2.setId(2);
        r2.setName(ERoles.ROLE_BUYER);

        return List.of(r1, r2);
    }
}
