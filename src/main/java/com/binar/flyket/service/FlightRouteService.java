package com.binar.flyket.service;

import com.binar.flyket.dto.model.FlightRouteDetailDTO;
import com.binar.flyket.dto.request.AirportRouteRequest;
import com.binar.flyket.dto.request.UpdateRouteRequest;

import java.util.List;

public interface FlightRouteService {

    Boolean updateRoute(String routeCode, UpdateRouteRequest updateRouteRequest);
    Boolean addAirportRoute(AirportRouteRequest airportRouteRequest);

    Boolean deleteById(String id);

    List<FlightRouteDetailDTO> getAllRoute();

}
