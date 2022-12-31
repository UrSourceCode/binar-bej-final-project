package com.binar.flyket.repository;

import com.binar.flyket.dto.model.FlightScheduleDetailDTO;
import com.binar.flyket.model.AircraftClass;
import com.binar.flyket.model.FlightSchedule;
import com.binar.flyket.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface FlightScheduleRepository extends JpaRepository<FlightSchedule, String> {

    @Query(value = "SELECT " +
            "NEW com.binar.flyket.dto.model.FlightScheduleDetailDTO(fs.id, " +
            "fs.departureTime, fs.arrivalTime, acd.aircraftClass, " +
            "acd.aircraft.type, " +
            "fr.fromAirport.IATACode, fr.fromAirport.name, fr.fromAirport.city, " +
            "fr.toAirport.IATACode, fr.toAirport.name, fr.toAirport.city, " +
            "fr.hours, fr.minutes, acd.price, acd.maxBaggage, acd.maxCabin) " +
            "FROM FlightSchedule AS fs " +
            "JOIN fs.aircraftDetail AS acd " +
            "JOIN fs.flightRoute AS fr " +
            "WHERE fs.aircraftDetail.id = acd.id " +
            "AND fs.flightRoute.id = fr.id " +
            "AND fs.status = :status")
    Page<FlightScheduleDetailDTO> findFlightScheduleDetail(
            @Param("status") Status status, Pageable pageable);

    @Query(value = "SELECT " +
            "NEW com.binar.flyket.dto.model.FlightScheduleDetailDTO(fs.id, " +
            "fs.departureTime, fs.arrivalTime, acd.aircraftClass, " +
            "acd.aircraft.type, " +
            "fr.fromAirport.IATACode, fr.fromAirport.name, fr.fromAirport.city, " +
            "fr.toAirport.IATACode, fr.toAirport.name, fr.toAirport.city, " +
            "fr.hours, fr.minutes, acd.price, acd.maxBaggage, acd.maxCabin) " +
            "FROM FlightSchedule AS fs " +
            "JOIN fs.aircraftDetail AS acd " +
            "JOIN fs.flightRoute AS fr " +
            "WHERE fs.id = :id " +
            "AND fs.status = :status")
    Optional<FlightScheduleDetailDTO> findFlightScheduleDetailById(
            @Param("id") String id,
            @Param("status") Status status);

    @Query(value = "SELECT " +
            "NEW com.binar.flyket.dto.model.FlightScheduleDetailDTO(fs.id, " +
            "fs.departureTime, fs.arrivalTime, acd.aircraftClass, " +
            "acd.aircraft.type, " +
            "fr.fromAirport.IATACode, fr.fromAirport.name, fr.fromAirport.city, " +
            "fr.toAirport.IATACode, fr.toAirport.name, fr.toAirport.city, " +
            "fr.hours, fr.minutes, acd.price, acd.maxBaggage, acd.maxCabin) " +
            "FROM FlightSchedule AS fs " +
            "JOIN fs.aircraftDetail AS acd " +
            "JOIN fs.flightRoute AS fr " +
            "WHERE DATE(fs.departureTime) = :date " +
            "AND fs.flightRoute.fromAirport.IATACode = :originAirport " +
            "AND fs.flightRoute.toAirport.IATACode = :destinationAirport " +
            "AND fs.aircraftDetail.aircraftClass = :aircraftClass " +
            "AND fs.status = :status ")
    Page<FlightScheduleDetailDTO> searchFlightScheduleByAirportAndDate(
            @Param(value = "originAirport") String originAirport,
            @Param(value = "destinationAirport") String destinationAirport,
            @Param(value = "date") LocalDate flightDate,
            @Param(value = "aircraftClass") AircraftClass aircraftClass,
            @Param(value = "status") Status status,
            Pageable pageable);
}
