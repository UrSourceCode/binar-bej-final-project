package com.binar.flyket.dto.mapper;

import com.binar.flyket.dto.model.AircraftDTO;
import com.binar.flyket.model.Aircraft;

public class AircraftMapper {

    public static AircraftDTO toDto(Aircraft aircraft) {
        AircraftDTO aircraftDTO = new AircraftDTO();
        aircraftDTO.setId(aircraft.getId());
        aircraftDTO.setType(aircraft.getType());
        return aircraftDTO;
    }
}
