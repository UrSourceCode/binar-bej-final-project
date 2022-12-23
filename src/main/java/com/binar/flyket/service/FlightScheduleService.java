package com.binar.flyket.service;

import com.binar.flyket.dto.model.FlightScheduleDetailDTO;
import com.binar.flyket.dto.request.FlightScheduleRequest;
import com.binar.flyket.dto.request.UpdateScheduleRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface FlightScheduleService {

    Boolean addFlightSchedule(FlightScheduleRequest flightScheduleRequest);

    Boolean deleteFlightScheduleById(String id);

    List<FlightScheduleDetailDTO> getFlightScheduleDetails(Pageable pageable);

    FlightScheduleDetailDTO getFlightScheduleDetailById(String id);

    List<FlightScheduleDetailDTO> searchFlightSchedule(
            String originAirportId,
            String destinationAirportId,
            String aircraftClass,
            LocalDate flightDate,
            Pageable pageable);

    Boolean updateFlightSchedule(String scheduleId, UpdateScheduleRequest updateScheduleRequest);

}
