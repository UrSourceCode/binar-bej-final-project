package com.binar.flyket.service;

import com.binar.flyket.dto.model.FlightScheduleDetailDTO;
import com.binar.flyket.dto.request.FlightScheduleRequest;
import com.binar.flyket.exception.ExceptionType;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.model.AircraftDetail;
import com.binar.flyket.model.FlightRoute;
import com.binar.flyket.model.FlightSchedule;
import com.binar.flyket.repository.AircraftDetailRepository;
import com.binar.flyket.repository.AirportRouteRepository;
import com.binar.flyket.repository.FlightScheduleRepository;
import com.binar.flyket.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightScheduleServiceImpl implements FlightScheduleService {

    private static Logger LOGGER = LoggerFactory.getLogger(FlightScheduleServiceImpl.class);

    private FlightScheduleRepository flightScheduleRepository;

    private AircraftDetailRepository aircraftDetailRepository;

    private AirportRouteRepository airportRouteRepository;

    public FlightScheduleServiceImpl(FlightScheduleRepository flightScheduleRepository,
                                     AircraftDetailRepository aircraftDetailRepository,
                                     AirportRouteRepository airportRouteRepository) {
        this.flightScheduleRepository = flightScheduleRepository;
        this.aircraftDetailRepository = aircraftDetailRepository;
        this.airportRouteRepository = airportRouteRepository;
    }

    @Override
    public Boolean addFlightSchedule(FlightScheduleRequest flightScheduleRequest) {
        Optional<AircraftDetail> aircraftDetail = aircraftDetailRepository.findById(flightScheduleRequest.getAircraftDetailId());
        Optional<FlightRoute> route = airportRouteRepository.findById(flightScheduleRequest.getRouteId());

        if(aircraftDetail.isEmpty()) {
            LOGGER.info("Aircraft: "+ Constants.NOT_FOUND_MSG);
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, "Aircraft " + Constants.NOT_FOUND_MSG);
        }

        if(route.isEmpty()) {
            LOGGER.info("Route: "+ Constants.NOT_FOUND_MSG);
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, "Route " + Constants.NOT_FOUND_MSG);
        }

        FlightSchedule flightSchedule = new FlightSchedule();
        flightSchedule.setId(flightScheduleRequest.getId());
        flightSchedule.setFlightDate(flightScheduleRequest.getFlightDate());
        flightSchedule.setArrivalTime(flightScheduleRequest.getArrivalTime());
        flightSchedule.setDepartureTime(flightScheduleRequest.getDepartureTime());
        flightSchedule.setAircraftDetail(aircraftDetail.get());
        flightSchedule.setFlightRoute(route.get());

        flightScheduleRepository.save(flightSchedule);

        return true;
    }

    @Override
    public Boolean deleteFlightScheduleById(String id) {
        Optional<FlightSchedule> flightSchedule = flightScheduleRepository.findById(id);
        if(flightSchedule.isPresent()) {
            flightScheduleRepository.delete(flightSchedule.get());
            return true;
        }
        LOGGER.info("Flight Schedule : " + Constants.NOT_FOUND_MSG);
        throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.NOT_FOUND_MSG);
    }

    @Override
    public List<FlightScheduleDetailDTO> getFlightScheduleDetails() {
        return flightScheduleRepository.findFlightScheduleDetail();
    }

    @Override
    public FlightScheduleDetailDTO getFlightScheduleDetailById(String id) {
        Optional<FlightScheduleDetailDTO> flightSchedule = flightScheduleRepository.findFlightScheduleDetailById(id);
        if(flightSchedule.isPresent()) {
            return flightSchedule.get();
        }
        LOGGER.info("Flight schedule with id "+ id + "  : " + Constants.NOT_FOUND_MSG);
        throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.NOT_FOUND_MSG);
    }
}
