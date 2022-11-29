package com.binar.flyket.service;

import com.binar.flyket.dto.model.FlightRouteDetailDTO;
import com.binar.flyket.dto.request.AirportRouteRequest;
import com.binar.flyket.dto.request.UpdateRouteRequest;
import com.binar.flyket.exception.ExceptionType;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.model.Airport;
import com.binar.flyket.model.FlightRoute;
import com.binar.flyket.repository.AirportRepository;
import com.binar.flyket.repository.AirportRouteRepository;
import com.binar.flyket.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FlightRouteServiceImpl implements FlightRouteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightRouteServiceImpl.class);

    private AirportRepository airportRepository;
    private AirportRouteRepository airportRouteRepository;

    public FlightRouteServiceImpl(AirportRepository airportRepository, AirportRouteRepository airportRouteRepository) {
        this.airportRepository = airportRepository;
        this.airportRouteRepository = airportRouteRepository;
    }

    @Override
    @Transactional
    public Boolean updateRoute(String routeCode, UpdateRouteRequest updateRouteRequest) {
        Optional<FlightRoute> flightRoute = airportRouteRepository.findById(routeCode);
        if(flightRoute.isPresent()) {
            Optional<Airport> fromAirport = airportRepository.findById(updateRouteRequest.getOriginAirportCode().toUpperCase());
            Optional<Airport> toAirport = airportRepository.findById(updateRouteRequest.getDestinationAirportCode().toUpperCase());

            isExistRoute(fromAirport, toAirport);

            FlightRoute flightRouteModel = flightRoute.get();

            flightRouteModel.setUpdatedAt(LocalDateTime.now());
            flightRouteModel.setMinutes(updateRouteRequest.getMinutes());
            flightRouteModel.setHours(updateRouteRequest.getHours());
            flightRouteModel.setFromAirport(fromAirport.get());
            flightRouteModel.setToAirport(fromAirport.get());

            airportRouteRepository.save(flightRouteModel);

            return true;

        }
        throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.ROUTE_NOT_FOUND_MSG);
    }


    @Override
    @Transactional
    public Boolean addAirportRoute(AirportRouteRequest airportRouteRequest) {
        Optional<Airport> fromAirport = airportRepository.findById(airportRouteRequest.getOriginAirportCode().toUpperCase());
        Optional<Airport> toAirport = airportRepository.findById(airportRouteRequest.getDestinationAirportCode().toUpperCase());

        isExistRoute(fromAirport, toAirport);

        StringBuilder randId = new StringBuilder();
        for(String id : Constants.randomIdentifier(airportRouteRequest.getOriginAirportCode()
                + airportRouteRequest.getDestinationAirportCode())) {
            randId.append(id);
        }

        FlightRoute flightRoute = new FlightRoute();
        flightRoute.setId(randId.toString());
        flightRoute.setFromAirport(fromAirport.get());
        flightRoute.setToAirport(toAirport.get());
        flightRoute.setHours(airportRouteRequest.getHours());
        flightRoute.setMinutes(airportRouteRequest.getMinutes());
        flightRoute.setCreatedAt(LocalDateTime.now());
        flightRoute.setUpdatedAt(LocalDateTime.now());

        LOGGER.info("AirportRoute saved");

        airportRouteRepository.save(flightRoute);

        return true;
    }

    @Override
    public Boolean deleteById(String id) {
        Optional<FlightRoute> airportRoute = airportRouteRepository.findById(id);
        if(airportRoute.isPresent()) {
            airportRouteRepository.delete(airportRoute.get());
            LOGGER.info("AirportRoute:  deleted");
            return true;
        }
        throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.ROUTE_NOT_FOUND_MSG);
    }

    @Override
    public List<FlightRouteDetailDTO> getAllRoute() {
        return airportRouteRepository.findAllRoute();
    }

    private void isExistRoute(Optional<Airport> fromAirport, Optional<Airport> toAirport) {
        if(fromAirport.isEmpty()) {
            LOGGER.info("Origin Airport not found");
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.FROM_AIRPORT_NOT_FOUND_MSG);
        }
        if(toAirport.isEmpty()) {
            LOGGER.info("Destination Airport not found");
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.TO_AIRPORT_NOT_FOUND_MSG);
        }
    }
}
