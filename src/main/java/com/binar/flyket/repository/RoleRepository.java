package com.binar.flyket.repository;

import com.binar.flyket.model.user.ERoles;
import com.binar.flyket.model.user.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Integer> {
    Optional<Roles> findByName(ERoles name);
}
