package com.binar.flyket.repository;

import com.binar.flyket.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AirportRepository extends JpaRepository<Airport, Integer> {

    Optional<Airport> findByName();
}
