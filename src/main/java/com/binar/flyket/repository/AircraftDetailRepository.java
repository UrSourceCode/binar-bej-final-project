package com.binar.flyket.repository;

import com.binar.flyket.model.AircraftDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AircraftDetailRepository extends JpaRepository<AircraftDetail, String> {
}
