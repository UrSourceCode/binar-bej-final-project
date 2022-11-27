package com.binar.flyket.service;

import com.binar.flyket.dto.model.AirportRouteDetailDTO;
import com.binar.flyket.dto.request.AirportRouteRequest;
import com.binar.flyket.model.AirportRoute;

import java.util.List;

public interface AirportRouteService {

    AirportRouteDetailDTO addAirportRoute(AirportRouteRequest airportRouteRequest);

    AirportRoute deleteById(String id);

    List<AirportRouteDetailDTO> getAllRoute();

}
