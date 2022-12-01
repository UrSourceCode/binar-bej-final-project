package com.binar.flyket.service;

import com.binar.flyket.dto.mapper.RoleMapper;
import com.binar.flyket.dto.model.RoleDTO;
import com.binar.flyket.dto.request.RoleRequest;
import com.binar.flyket.exception.ExceptionType;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.model.user.ERoles;
import com.binar.flyket.model.user.Roles;
import com.binar.flyket.repository.RoleRepository;
import com.binar.flyket.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Boolean addRole(RoleRequest request) {
        ERoles eRoles = ERoles.getRole(request.getRoleName());
        Optional<Roles> role = roleRepository.findByName(eRoles);

        if(Boolean.FALSE.equals(ERoles.checkRole(request.getRoleName())))
            throw FlyketException.throwException(ExceptionType.DUPLICATE_ENTITY, HttpStatus.NOT_FOUND, Constants.NOT_FOUND_MSG);


        if(role.isPresent())
            throw FlyketException.throwException(ExceptionType.DUPLICATE_ENTITY, HttpStatus.CONFLICT, Constants.ALREADY_EXIST_MSG);

        Roles roleModel = new Roles();
        roleModel.setName(eRoles);
        roleRepository.save(roleModel);

        return true;
    }

    @Override
    public List<RoleDTO> getRoles() {
        return roleRepository.findAll()
                .stream().map(RoleMapper::toDto).toList();
    }

    @Override
    public Boolean deleteRoleById(String role) {
        if(Boolean.FALSE.equals(ERoles.checkRole(role)))
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.NOT_FOUND_MSG);

        ERoles eRoles = ERoles.getRole(role);
        Optional<Roles> roles = roleRepository.findByName(eRoles);
        if(roles.isPresent()) {
            roleRepository.delete(roles.get());
            return true;
        }

        throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.NOT_FOUND_MSG);
    }

    @Override
    public RoleDTO getRoleByName(String role) {
        ERoles eRoles = ERoles.getRole(role);
        Optional<Roles> roles = roleRepository.findByName(eRoles);

        if(Boolean.FALSE.equals(ERoles.checkRole(role)))
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.NOT_FOUND_MSG);

        if(roles.isEmpty())
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.NOT_FOUND_MSG);

        return RoleMapper.toDto(roles.get());
    }

}
