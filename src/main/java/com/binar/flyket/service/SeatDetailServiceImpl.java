package com.binar.flyket.service;

import com.binar.flyket.dto.request.SeatRequest;
import com.binar.flyket.exception.ExceptionType;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.model.AircraftDetail;
import com.binar.flyket.model.SeatDetail;
import com.binar.flyket.repository.AircraftDetailRepository;
import com.binar.flyket.repository.SeatDetailRepository;
import com.binar.flyket.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SeatDetailServiceImpl implements SeatDetailService {

    private SeatDetailRepository seatDetailRepository;

    private AircraftDetailRepository aircraftDetailRepository;

    public SeatDetailServiceImpl(SeatDetailRepository seatDetailRepository, AircraftDetailRepository aircraftDetailRepository) {
        this.seatDetailRepository = seatDetailRepository;
        this.aircraftDetailRepository = aircraftDetailRepository;
    }

    @Override
    public Boolean addSeat(SeatRequest request) {

        Optional<SeatDetail> seatDetail = seatDetailRepository.findById(request.getSeatNo().toUpperCase());
        if(seatDetail.isPresent())
            throw FlyketException.throwException(ExceptionType.DUPLICATE_ENTITY, HttpStatus.CONFLICT, Constants.ALREADY_EXIST_MSG);

        Optional<AircraftDetail> aircraftDetail = aircraftDetailRepository.findById(request.getAircraftDetailId());
        if(aircraftDetail.isEmpty())
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, "Aircraft Detail " + Constants.NOT_FOUND_MSG);

        SeatDetail seatDetailModel = new SeatDetail();
        seatDetailModel.setId(seatDetailModel.getId().toUpperCase());
        seatDetailModel.setStatus(false);
        return true;
    }

}
