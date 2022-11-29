package com.binar.flyket.service;

import com.binar.flyket.dto.model.FlightScheduleDetailDTO;
import com.binar.flyket.dto.model.SearchScheduleRequest;
import com.binar.flyket.dto.request.FlightScheduleRequest;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FlightScheduleService {

    Boolean addFlightSchedule(FlightScheduleRequest flightScheduleRequest);

    Boolean deleteFlightScheduleById(String id);

    List<FlightScheduleDetailDTO> getFlightScheduleDetails();

    FlightScheduleDetailDTO getFlightScheduleDetailById(String id);

    List<FlightScheduleDetailDTO> searchFlightSchedule(SearchScheduleRequest searchScheduleRequest);

}
