package com.binar.flyket.repository;

import com.binar.flyket.model.SeatDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SeatDetailRepository extends JpaRepository<SeatDetail, Integer> {

    @Query(value = "SELECT sd FROM SeatDetail AS sd " +
            "WHERE sd.aircraftDetail.id =:aircraft_id AND sd.no =:seat_no AND sd.row =:seat_row")
    Optional<SeatDetail> helloGetSeat(
            @Param("aircraft_id") String aircraftId,
            @Param("seat_row") String row,
            @Param("seat_no") Integer no);

}
