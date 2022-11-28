package com.binar.flyket.service;

import com.binar.flyket.dto.request.AircraftDetailRequest;
import com.binar.flyket.repository.AircraftDetailRepository;
import org.springframework.stereotype.Service;

@Service
public class AircraftDetailServiceImpl implements AircraftDetailService {

    private AircraftDetailRepository aircraftDetailRepository;


    public AircraftDetailServiceImpl(AircraftDetailRepository aircraftDetailRepository) {
        this.aircraftDetailRepository = aircraftDetailRepository;
    }


    @Override
    public boolean addAircraftDetail(AircraftDetailRequest aircraftDetailRequest) {
        return false;
    }
}
