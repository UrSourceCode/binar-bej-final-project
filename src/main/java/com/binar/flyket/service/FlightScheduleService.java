package com.binar.flyket.service;

import com.binar.flyket.dto.model.FlightScheduleDetailDTO;
import com.binar.flyket.dto.model.SearchScheduleRequest;
import com.binar.flyket.dto.request.FlightScheduleRequest;
import com.binar.flyket.dto.request.UpdateScheduleRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FlightScheduleService {

    Boolean addFlightSchedule(FlightScheduleRequest flightScheduleRequest);

    Boolean deleteFlightScheduleById(String id);

    List<FlightScheduleDetailDTO> getFlightScheduleDetails();

    FlightScheduleDetailDTO getFlightScheduleDetailById(String id);

    List<FlightScheduleDetailDTO> searchFlightSchedule(
            Pageable pageable,
            SearchScheduleRequest searchScheduleRequest);

    Boolean updateFlightSchedule(String scheduleId, UpdateScheduleRequest updateScheduleRequest);

}
