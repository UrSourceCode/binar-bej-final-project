package com.binar.flyket.service;

import com.binar.flyket.dto.model.FlightScheduleDetailDTO;
import com.binar.flyket.dto.model.SearchScheduleRequest;
import com.binar.flyket.dto.request.FlightScheduleRequest;
import com.binar.flyket.dto.request.UpdateScheduleRequest;
import com.binar.flyket.exception.ExceptionType;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.model.AircraftClass;
import com.binar.flyket.model.AircraftDetail;
import com.binar.flyket.model.FlightRoute;
import com.binar.flyket.model.FlightSchedule;
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

        if(aircraftDetail.isEmpty()) {
            LOGGER.info("Aircraft: "+ Constants.NOT_FOUND_MSG);
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, "Aircraft " + Constants.NOT_FOUND_MSG);
        }

        Optional<FlightRoute> route = airportRouteRepository.findById(flightScheduleRequest.getRouteId());

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
    public List<FlightScheduleDetailDTO> getFlightScheduleDetails(Pageable paging) {
        return flightScheduleRepository.findFlightScheduleDetail(paging).getContent();
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

    @Override
    public List<FlightScheduleDetailDTO> searchFlightSchedule(
            Pageable paging,
            SearchScheduleRequest searchScheduleRequest) {

        AircraftClass aircraftClass = AircraftClass.getClass(searchScheduleRequest.getAircraftClass());

        Page<FlightScheduleDetailDTO> pageFlight = flightScheduleRepository.searchFlightScheduleByAirportAndDate(
                searchScheduleRequest.getOriginAirportId().toUpperCase().trim(),
                searchScheduleRequest.getDestinationAirportId().toUpperCase().trim(),
                searchScheduleRequest.getFlightDate(),
                aircraftClass, paging);

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

        FlightSchedule flightSchedule = new FlightSchedule();
        flightSchedule.setId(scheduleId);
        flightSchedule.setFlightRoute(flightRoute.get());
        flightSchedule.setAircraftDetail(aircraftDetail.get());
        flightSchedule.setFlightDate(updateScheduleRequest.getFlightDate());
        flightSchedule.setDepartureTime(updateScheduleRequest.getDepartureTime());
        flightSchedule.setArrivalTime(updateScheduleRequest.getArrivalTime());

        flightScheduleRepository.save(flightSchedule);
        return true;
    }
}
