package com.binar.flyket.controller;

import com.binar.flyket.dto.request.RoleRequest;
import com.binar.flyket.dto.response.Response;
import com.binar.flyket.dto.response.ResponseError;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.service.RoleService;
import com.binar.flyket.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin(value = "*", maxAge = 3600L)
@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRole(
            @RequestBody RoleRequest roleRequest) {
        try {
            roleService.addRole(roleRequest);
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(), Constants.SUCCESS_MSG, null));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        } catch (FlyketException.DuplicateEntityException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/role-name")
    public ResponseEntity<?> getRoleByName(@RequestParam("name") String  role) {
        try {
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(), Constants.SUCCESS_MSG,
                    roleService.getRoleByName(role)));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }

//    @PreAuthorize("hasRole('ADMIN')")
//    @DeleteMapping("/delete")
//    public ResponseEntity<?> deleteRoleByName(@RequestParam("role") String role) {
//        try {
//            roleService.deleteRoleById(role);
//            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(), Constants.DELETED_MSG, null));
//        } catch (FlyketException.EntityNotFoundException e) {
//            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
//        }
//    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getRoles() {
       return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(),
               Constants.SUCCESS_MSG, roleService.getRoles()));
    }
}
