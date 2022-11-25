package com.binar.flyket.repository;

import com.binar.flyket.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {
    Boolean existsByCode(String code);
}
