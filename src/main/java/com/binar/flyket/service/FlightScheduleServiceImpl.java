package com.binar.flyket.service;

import com.binar.flyket.dto.model.FlightScheduleDetailDTO;
import com.binar.flyket.dto.request.FlightScheduleRequest;
import com.binar.flyket.dto.request.UpdateScheduleRequest;
import com.binar.flyket.exception.ExceptionType;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.model.*;
import com.binar.flyket.repository.AircraftDetailRepository;
import com.binar.flyket.repository.AirportRouteRepository;
import com.binar.flyket.repository.FlightScheduleRepository;
import com.binar.flyket.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

        if(aircraftDetail.isEmpty()) {
            LOGGER.info("Aircraft: "+ Constants.NOT_FOUND_MSG);
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, "Aircraft " + Constants.NOT_FOUND_MSG);
        }

        Optional<FlightRoute> route = airportRouteRepository.findById(flightScheduleRequest.getRouteId());

        if(route.isEmpty()) {
            LOGGER.info("Route: "+ Constants.NOT_FOUND_MSG);
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, "Route " + Constants.NOT_FOUND_MSG);
        }


        String[] randId = UUID.randomUUID().toString().toUpperCase().split("-");

        FlightSchedule flightSchedule = new FlightSchedule();
        flightSchedule.setId("sc" + randId[0] + randId[1]);

        String[] randomId = UUID.randomUUID().toString().toUpperCase().split("-");
        String scheduleId = "sc-" + randomId[0] + randomId[1];

        FlightSchedule flightSchedule = new FlightSchedule();
        flightSchedule.setId(scheduleId);
        flightSchedule.setArrivalTime(flightScheduleRequest.getArrivalTime());
        flightSchedule.setDepartureTime(flightScheduleRequest.getDepartureTime());
        flightSchedule.setFlightDate(flightScheduleRequest.getFlightDate());
        flightSchedule.setAircraftDetail(aircraftDetail.get());
        flightSchedule.setFlightRoute(route.get());
        flightSchedule.setStatus(Status.ACTIVE);
        flightSchedule.setUpdatedAt(LocalDateTime.now());
        flightSchedule.setCreatedAt(LocalDateTime.now());

        flightScheduleRepository.save(flightSchedule);

        return true;
    }

    @Override
    public Boolean deleteFlightScheduleById(String id) {
        Optional<FlightSchedule> flightSchedule = flightScheduleRepository.findById(id);
        if(flightSchedule.isPresent()) {
            FlightSchedule flModel = flightSchedule.get();
            flModel.setStatus(Status.DELETE);
            flModel.setUpdatedAt(LocalDateTime.now());
            flightScheduleRepository.save(flModel);
            return true;
        }
        LOGGER.info("Flight Schedule : " + Constants.NOT_FOUND_MSG);
        throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.NOT_FOUND_MSG);
    }

    @Override
    public List<FlightScheduleDetailDTO> getFlightScheduleDetails(Pageable paging) {
        return flightScheduleRepository.findFlightScheduleDetail(Status.ACTIVE, paging).getContent();
    }

    @Override
    public FlightScheduleDetailDTO getFlightScheduleDetailById(String id) {
        Optional<FlightScheduleDetailDTO> flightSchedule = flightScheduleRepository.findFlightScheduleDetailById(id, Status.ACTIVE);
        if(flightSchedule.isPresent()) {
            return flightSchedule.get();
        }
        LOGGER.info("Flight schedule with id "+ id + "  : " + Constants.NOT_FOUND_MSG);
        throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.NOT_FOUND_MSG);
    }

    @Override
    public List<FlightScheduleDetailDTO> searchFlightSchedule(String originAirportId,
                                                              String destinationAirportId,
                                                              String aircraftClass,
                                                              LocalDate flightDate,
                                                              Pageable pageable) {
        AircraftClass ac = AircraftClass.getClass(aircraftClass);
        LOGGER.info("AircraftClass : " + aircraftClass );

        Page<FlightScheduleDetailDTO> pageFlight = flightScheduleRepository.searchFlightScheduleByAirportAndDate(
                originAirportId.toUpperCase().trim(),
                destinationAirportId.toUpperCase().trim(), flightDate,
                ac, Status.ACTIVE, pageable);

        return pageFlight.getContent();
    }

    @Override
    public Boolean updateFlightSchedule(String scheduleId, UpdateScheduleRequest updateScheduleRequest) {
        Optional<AircraftDetail> aircraftDetail = aircraftDetailRepository.findById(updateScheduleRequest.getAircraftDetailId());

        if(aircraftDetail.isEmpty())
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND,"AircraftDetail : " + Constants.NOT_FOUND_MSG);

        Optional<FlightRoute> flightRoute = airportRouteRepository.findById(updateScheduleRequest.getFlightRouteId());

        if(flightRoute.isEmpty())
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, "Route : " + Constants.NOT_FOUND_MSG);

        Optional<FlightSchedule> flightSchedule = flightScheduleRepository.findById(scheduleId);
        if(flightSchedule.isEmpty())
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, "Flight Schedule : " + Constants.NOT_FOUND_MSG);

        FlightSchedule flightScheduleModel = flightSchedule.get();
        flightScheduleModel.setFlightRoute(flightRoute.get());
        flightScheduleModel.setAircraftDetail(aircraftDetail.get());
        flightScheduleModel.setDepartureTime(updateScheduleRequest.getDepartureTime());
        flightScheduleModel.setArrivalTime(updateScheduleRequest.getArrivalTime());
        flightScheduleModel.setUpdatedAt(LocalDateTime.now());

        flightScheduleRepository.save(flightScheduleModel);
        return true;
    }
}
