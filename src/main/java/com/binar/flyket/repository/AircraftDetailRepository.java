package com.binar.flyket.repository;

import com.binar.flyket.dto.model.AircraftDetailDTO;
import com.binar.flyket.model.AircraftDetail;
import com.binar.flyket.repository.query.AircraftDetailQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AircraftDetailRepository extends JpaRepository<AircraftDetail, String> {

    @Query(value = AircraftDetailQuery.AIRCRAFT_DETAIL_JOIN)
    List<AircraftDetailDTO> findAllAircraftDetail();
}
