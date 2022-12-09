package com.binar.flyket.service;

import com.binar.flyket.dto.model.RoleDTO;
import com.binar.flyket.dto.request.RoleRequest;

import java.util.List;

public interface RoleService {

    Boolean addRole(RoleRequest request);

    List<RoleDTO> getRoles();

    Boolean deleteRoleById(String role);

    RoleDTO getRoleByName(String role);
}
