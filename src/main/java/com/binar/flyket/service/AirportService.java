package com.binar.flyket.service;

import com.binar.flyket.dto.model.AirportDTO;
import com.binar.flyket.dto.model.AirportDetailDTO;
import com.binar.flyket.dto.request.InputAirportRequest;

import java.util.List;


public interface AirportService {

    InputAirportRequest addAirport(InputAirportRequest inputAirportRequest);

    AirportDTO deleteAirportById(String IATACode);

    AirportDetailDTO getAirportById(String IATACode);

    List<AirportDTO> getAirports();

    // TODO : update airport

}
