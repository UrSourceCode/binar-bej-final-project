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

import java.util.List;
import java.util.Optional;

@Service
public class SeatDetailServiceImpl implements SeatDetailService {

    private SeatDetailRepository seatDetailRepository;

    public SeatDetailServiceImpl(SeatDetailRepository seatDetailRepository) {
        this.seatDetailRepository = seatDetailRepository;
    }

    @Override
    public Boolean addSeat(SeatRequest request) {

        Optional<SeatDetail> seatDetail = seatDetailRepository.findById(request.getSeatNo().toUpperCase());
        if(seatDetail.isPresent())
            throw FlyketException.throwException(ExceptionType.DUPLICATE_ENTITY, HttpStatus.CONFLICT, Constants.ALREADY_EXIST_MSG);

        SeatDetail seatDetailModel = new SeatDetail();
        seatDetailModel.setId(request.getSeatNo().toUpperCase());
        seatDetailModel.setStatus(false);

        seatDetailRepository.save(seatDetailModel);
        return true;
    }

    @Override
    public List<SeatDetail> getAll() { return seatDetailRepository.findAll();}

}
