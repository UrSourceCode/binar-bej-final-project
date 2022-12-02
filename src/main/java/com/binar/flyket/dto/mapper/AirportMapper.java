package com.binar.flyket.dto.mapper;


import com.binar.flyket.dto.model.AirportDTO;
import com.binar.flyket.model.Airport;

public class AirportMapper {

    public static AirportDTO toDto(Airport airport) {
        AirportDTO airportDTO = new AirportDTO();
        airportDTO.setName(airport.getName());
        airportDTO.setCity(airport.getCity());
        airportDTO.setIATACode(airport.getIATACode());
        return airportDTO;
    }
}
