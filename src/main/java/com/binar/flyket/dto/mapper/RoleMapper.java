package com.binar.flyket.dto.mapper;

import com.binar.flyket.dto.model.RoleDTO;
import com.binar.flyket.model.user.Roles;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public static RoleDTO toDto(Roles roles) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(roles.getId());
        roleDTO.setERoles(roles.getName());
        return roleDTO;
    }
}
