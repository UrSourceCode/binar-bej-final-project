package com.binar.flyket.repository;

import com.binar.flyket.model.AvailableSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvailableSeatRepository extends JpaRepository<AvailableSeat, Integer> {

    @Query(value = "SELECT avs FROM AvailableSeat avs WHERE avs.aircraftDetail.id =: aircraft_id")
    List<AvailableSeat> findAvailableSeatByAircraft(@Param("aircraft_id") String aircraftId);
}
