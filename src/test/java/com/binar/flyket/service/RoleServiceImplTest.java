package com.binar.flyket.service;

import com.binar.flyket.dto.model.RoleDTO;
import com.binar.flyket.dto.request.RoleRequest;
import com.binar.flyket.dummy.RoleDummies;
import com.binar.flyket.model.user.ERoles;
import com.binar.flyket.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddRole() {
        RoleRequest request = new RoleRequest();
        request.setRoleName("admin");

        ERoles eRoles = ERoles.getRole(request.getRoleName());

        Mockito.when(roleRepository.findByName(eRoles))
                .thenReturn(Optional.empty());

        var actualValue = roleService.addRole(request);
        var expectedValue = true;

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void testGetRoles() {
        List<RoleDTO> expectedValue = RoleDummies.roles()
                .stream().map(it -> {
                    RoleDTO r = new RoleDTO();
                    r.setId(it.getId());
                    r.setERoles(it.getName());
                    return r;
                }).toList();

        Mockito.when(roleRepository.findAll())
                .thenReturn(RoleDummies.roles());

        var actualValue = roleService.getRoles();

        Assertions.assertEquals(expectedValue.size(), actualValue.size());
        Assertions.assertEquals(expectedValue.get(0).getERoles(), actualValue.get(0).getERoles());
        Assertions.assertEquals(expectedValue.get(1).getERoles(), actualValue.get(1).getERoles());

    }

    @Test
    void testDeleteRoleByRoleName() {
        String roleName = "admin";

        ERoles eRoles = ERoles.getRole(roleName);

        Mockito.when(roleRepository.findByName(eRoles))
                .thenReturn(Optional.of(RoleDummies.roles().get(0)));
        Mockito.when(roleRepository.save(RoleDummies.roles().get(0)))
                .thenReturn(RoleDummies.roles().get(0));

        var actualValue = roleService.getRoleByName(roleName);
        var expectedValue = ERoles.ROLE_ADMIN;

        Assertions.assertNotNull(actualValue);
        Assertions.assertEquals(expectedValue, actualValue.getERoles());
    }

    @Test
    void testGetRoleByName() {
        String roleName = "admin";

        ERoles eRoles = ERoles.getRole(roleName);

        Mockito.when(roleRepository.findByName(eRoles))
                .thenReturn(Optional.of(RoleDummies.roles().get(0)));
        Mockito.when(roleRepository.save(RoleDummies.roles().get(0)))
                .thenReturn(RoleDummies.roles().get(0));

        var actualValue = roleService.getRoleByName(roleName);
        var expectedValue = ERoles.ROLE_ADMIN;

        Assertions.assertNotNull(actualValue);
        Assertions.assertEquals(expectedValue, actualValue.getERoles());
    }

}