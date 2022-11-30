package com.binar.flyket.service;

import com.binar.flyket.dto.model.AirportDTO;
import com.binar.flyket.dto.model.AirportDetailDTO;
import com.binar.flyket.dto.request.InputAirportRequest;
import com.binar.flyket.dto.request.UpdateAirportRequest;

import java.util.List;
import java.util.Optional;

public interface AirportService {

    InputAirportRequest addAirport(InputAirportRequest inputAirportRequest);

    AirportDTO deleteAirportById(String IATACode);

    AirportDetailDTO getAirportById(String IATACode);

    List<AirportDetailDTO> getAirports();

    Boolean updateAirport(String IATACode, UpdateAirportRequest updateAirportRequest);
}
