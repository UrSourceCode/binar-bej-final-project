package com.binar.flyket.repository;

import com.binar.flyket.dto.model.FlightScheduleDetailDTO;
import com.binar.flyket.model.FlightSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.binar.flyket.repository.query.FlightScheduleQuery.*;

@Repository
public interface FlightScheduleRepository extends JpaRepository<FlightSchedule, String> {

    @Query(value = FLIGHT_SCHEDULE_DETAIL_JOIN)
    List<FlightScheduleDetailDTO> findFlightScheduleDetail();

    @Query(value = FLIGHT_SCHEDULE_DETAIL_JOIN_BY_ID)
    Optional<FlightScheduleDetailDTO> findFlightScheduleDetailById(@Param("id") String id);

    @Query(value = FLIGHT_SCHEDULE_DETAIL_BY_AIRPORT_AND_DATE)
    List<FlightScheduleDetailDTO> searchFlightScheduleByAirportAndDate(
            @Param("originAirport") String originAirport,
            @Param("destinationAirport") String destinationAirport,
            @Param("flightDate") LocalDate flightDate);
}
