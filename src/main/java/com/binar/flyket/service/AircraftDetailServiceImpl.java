package com.binar.flyket.service;

import com.binar.flyket.dto.model.AircraftDetailDTO;
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

import java.util.List;
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
        Optional<AircraftDetail> aircraftDetail = aircraftDetailRepository.findById(aircraftDetailRequest.getId());

        if(aircraftDetail.isPresent()) {
            LOGGER.info("Aircraft "+ Constants.ALREADY_EXIST_MSG);
            throw FlyketException.throwException(ExceptionType.DUPLICATE_ENTITY, HttpStatus.CONFLICT, Constants.ALREADY_EXIST_MSG);
        }

        if (aircraft.isEmpty()) {
            LOGGER.info("Aircraft "+ Constants.NOT_FOUND_MSG);
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.NOT_FOUND_MSG);
        }

        AircraftClass aircraftClass = AircraftClass.getClass(aircraftDetailRequest.getAircraftClass());

        AircraftDetail aircraftDetailModel = new AircraftDetail();
        aircraftDetailModel.setId(aircraftDetailRequest.getId());
        aircraftDetailModel.setAircraft(aircraft.get());
        aircraftDetailModel.setAircraftClass(aircraftClass);
        aircraftDetailModel.setPrice(aircraftDetailRequest.getAircraftPrice());
        aircraftDetailModel.setMaxBaggage(aircraftDetailRequest.getAircraftMaxBaggage());
        aircraftDetailModel.setMaxCabin(aircraftDetailRequest.getAircraftMaxCabin());

        aircraftDetailRepository.save(aircraftDetailModel);
        return true;
    }

    @Override
    public List<AircraftDetailDTO> getAllAircraftDetail() {
        return aircraftDetailRepository.findAllAircraftDetail();
    }

    @Override
    public boolean deleteById(String id) {
        Optional<AircraftDetail> aircraftDetail = aircraftDetailRepository.findById(id);
        if(aircraftDetail.isPresent()) {
            aircraftDetailRepository.deleteById(id);
            return true;
        }
        throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.NOT_FOUND_MSG);
    }

}
