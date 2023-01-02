package com.binar.flyket.service;


import com.binar.flyket.dto.model.AircraftDetailDTO;
import com.binar.flyket.dto.request.AircraftDetailRequest;

import java.util.List;

public interface AircraftDetailService {

    boolean addAircraftDetail(AircraftDetailRequest aircraftDetailRequest);

    List<AircraftDetailDTO> getAllAircraftDetail();

    boolean deleteById(String id);

}
