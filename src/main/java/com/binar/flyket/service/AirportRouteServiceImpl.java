package com.binar.flyket.service;

import com.binar.flyket.dto.model.AirportDetailDTO;
import com.binar.flyket.dto.model.AirportRouteDetailDTO;
import com.binar.flyket.dto.request.AirportRouteRequest;
import com.binar.flyket.exception.ExceptionType;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.model.Airport;
import com.binar.flyket.model.AirportRoute;
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
public class AirportRouteServiceImpl implements AirportRouteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AirportRouteServiceImpl.class);

    private AirportRepository airportRepository;
    private AirportRouteRepository airportRouteRepository;

    public AirportRouteServiceImpl(AirportRepository airportRepository, AirportRouteRepository airportRouteRepository) {
        this.airportRepository = airportRepository;
        this.airportRouteRepository = airportRouteRepository;
    }

    @Override
    @Transactional
    public AirportRouteDetailDTO addAirportRoute(AirportRouteRequest airportRouteRequest) {
        Optional<Airport> fromAirport = airportRepository.findById(airportRouteRequest.getFromAirport());
        Optional<Airport> toAirport = airportRepository.findById(airportRouteRequest.getToAirport());
        Optional<AirportDetailDTO> fromAirportDTO = airportRepository.findByIATACode(airportRouteRequest.getFromAirport());
        Optional<AirportDetailDTO> toAirportDTO = airportRepository.findByIATACode(airportRouteRequest.getToAirport());

        if(fromAirport.isEmpty()) {
            LOGGER.info("Origin Airport not found");
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.FROM_AIRPORT_NOT_FOUND_MSG);
        }
        if(toAirport.isEmpty()) {
            LOGGER.info("Destination Airport not found");
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.TO_AIRPORT_NOT_FOUND_MSG);
        }

        StringBuilder randId = new StringBuilder();
        for(String id : Constants.randomIdentifier(airportRouteRequest.getToAirport()
                + airportRouteRequest.getFromAirport())) {
            randId.append(id);
        }

        AirportRoute airportRoute = new AirportRoute();
        airportRoute.setId(randId.toString());
        airportRoute.setFromAirport(fromAirport.get());
        airportRoute.setToAirport(toAirport.get());
        airportRoute.setHours(airportRouteRequest.getHours());
        airportRoute.setMinutes(airportRouteRequest.getMinutes());
        airportRoute.setCreatedAt(LocalDateTime.now());
        airportRoute.setUpdatedAt(LocalDateTime.now());

        LOGGER.info("AirportRoute successfully saved");

        airportRouteRepository.save(airportRoute);

        AirportRouteDetailDTO airportRouteDetailDTO = new AirportRouteDetailDTO();
        airportRouteDetailDTO.setId(randId.toString());
        airportRouteDetailDTO.setTo(fromAirportDTO.get());
        airportRouteDetailDTO.setFrom(toAirportDTO.get());
        airportRouteDetailDTO.setMinutes(airportRouteRequest.getMinutes());
        airportRouteDetailDTO.setHours(airportRouteRequest.getHours());

        return airportRouteDetailDTO;
    }

    @Override
    public AirportRoute deleteById(String id) {
        Optional<AirportRoute> airportRoute = airportRouteRepository.findById(id);
        if(airportRoute.isPresent()) {
            AirportRoute airportRouteModel = airportRoute.get();
            airportRouteRepository.delete(airportRoute.get());
            LOGGER.info("AirportRoute deleted");
            return airportRouteModel;
        }
        throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.ROUTE_NOT_FOUND_MSG);
    }

    @Override
    public List<AirportRouteDetailDTO> getAllRoute() {
        return null;
    }
}
