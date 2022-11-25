package com.binar.flyket.service;

import com.binar.flyket.dto.model.AirportDTO;

import java.util.List;



public interface AirportService {

    boolean addAirport(AirportDTO airportDTO);

    boolean deleteAirportById(String IATACode);

    AirportDTO getAirportById(String IATACode);

    List<AirportDTO> getAirports();

    // TODO : update airport

}
