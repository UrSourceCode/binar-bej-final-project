package com.binar.flyket.repository;

import com.binar.flyket.dto.model.AirportDetailDTO;
import com.binar.flyket.model.Airport;
import com.binar.flyket.repository.query.AirportQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AirportRepository extends JpaRepository<Airport, String> {
    @Query(value = AirportQuery.AIRPORT_DETAIL_BY_ID)
    Optional<AirportDetailDTO> findByIATACode(@Param("IATACode") String IATACode);

    @Query(value = AirportQuery.AIRPORT_DETAIL_INNER_JOIN)
    List<AirportDetailDTO> findAllAirport();

}