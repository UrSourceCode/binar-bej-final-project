package com.binar.flyket.service;

import com.binar.flyket.dto.model.AircraftDTO;

import java.util.List;

public interface AircraftService {
    boolean addAircraft(AircraftDTO aircraftDTO);

    List<AircraftDTO> addAircraft(List<AircraftDTO> aircraftDTO);

    AircraftDTO deleteAircraft(Integer aircraftID);

    AircraftDTO updateAircraft(Integer aircraftID, AircraftDTO aircraftDTO);

    AircraftDTO getAircraftById(Integer aircraftID);

    List<AircraftDTO> getAircraft();
}
