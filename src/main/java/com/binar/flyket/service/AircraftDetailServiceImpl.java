package com.binar.flyket.service;

import com.binar.flyket.dto.request.AircraftDetailRequest;
import com.binar.flyket.exception.ExceptionType;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.model.Aircraft;
import com.binar.flyket.model.AircraftClass;
import com.binar.flyket.model.AircraftDetail;
import com.binar.flyket.repository.AircraftDetailRepository;
import com.binar.flyket.repository.AircraftRepository;
import com.binar.flyket.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AircraftDetailServiceImpl implements AircraftDetailService {

    private Logger LOGGER = LoggerFactory.getLogger(AircraftDetailServiceImpl.class);

    private AircraftDetailRepository aircraftDetailRepository;

    private AircraftRepository aircraftRepository;


    public AircraftDetailServiceImpl(AircraftDetailRepository aircraftDetailRepository, AircraftRepository aircraftRepository) {
        this.aircraftDetailRepository = aircraftDetailRepository;
        this.aircraftRepository = aircraftRepository;
    }

    @Override
    public boolean addAircraftDetail(AircraftDetailRequest aircraftDetailRequest) {
        Optional<Aircraft> aircraft = aircraftRepository.findById(aircraftDetailRequest.getAircraftNo());
        if (aircraft.isEmpty()) {
            LOGGER.info("Aircraft "+ Constants.NOT_FOUND_MSG);
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.NOT_FOUND_MSG);
        }

        AircraftClass aircraftClass = AircraftClass.getClass(aircraftDetailRequest.getAircraftClass());

        AircraftDetail aircraftDetail = new AircraftDetail();
        aircraftDetail.setAircraft(aircraft.get());
        aircraftDetail.setAircraftClass(aircraftClass);
        aircraftDetail.setPrice(aircraftDetailRequest.getAircraftPrice());
        aircraftDetail.setMaxBaggage(aircraftDetail.getMaxBaggage());

        aircraftDetailRepository.save(aircraftDetail);
        return true;
    }
}
