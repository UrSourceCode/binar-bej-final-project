package com.binar.flyket.repository;

import com.binar.flyket.dto.model.FlightScheduleDetailDTO;
import com.binar.flyket.model.FlightSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.binar.flyket.repository.query.FlightScheduleQuery.FLIGHT_SCHEDULE_DETAIL_JOIN;
import static com.binar.flyket.repository.query.FlightScheduleQuery.FLIGHT_SCHEDULE_DETAIL_JOIN_BY_ID;

@Repository
public interface FlightScheduleRepository extends JpaRepository<FlightSchedule, String> {

    @Query(value = FLIGHT_SCHEDULE_DETAIL_JOIN)
    List<FlightScheduleDetailDTO> findFlightScheduleDetail();

    @Query(value = FLIGHT_SCHEDULE_DETAIL_JOIN_BY_ID)
    Optional<FlightScheduleDetailDTO> findFlightScheduleDetailById(@Param("id") String id);
}
