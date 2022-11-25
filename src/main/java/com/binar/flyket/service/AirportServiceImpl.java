package com.binar.flyket.service;

import com.binar.flyket.dto.model.AirportDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportServiceImpl implements AirportService {
    @Override
    public boolean addAirport(AirportDTO airportDTO) {
        return false;
    }

    @Override
    public boolean deleteAirportById(String IATACode) {
        return false;
    }

    @Override
    public AirportDTO getAirportById(String IATACode) {
        return null;
    }

    @Override
    public List<AirportDTO> getAirports() {
        return null;
    }
}
