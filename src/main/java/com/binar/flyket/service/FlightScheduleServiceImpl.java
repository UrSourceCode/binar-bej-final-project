package com.binar.flyket.service;

import com.binar.flyket.repository.FlightScheduleRepository;
import org.springframework.stereotype.Service;

@Service
public class FlightScheduleServiceImpl implements FlightScheduleService {

    private FlightScheduleRepository flightScheduleRepository;

    public FlightScheduleServiceImpl(FlightScheduleRepository flightScheduleRepository) {
        this.flightScheduleRepository = flightScheduleRepository;
    }

    @Override
    public Boolean addFlightSchedule() {

        return null;
    }
}
