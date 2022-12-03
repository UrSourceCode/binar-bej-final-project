package com.binar.flyket.service;

import com.binar.flyket.dto.mapper.SeatMapper;
import com.binar.flyket.dto.model.SeatDTO;
import com.binar.flyket.exception.ExceptionType;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.model.Seat;
import com.binar.flyket.model.SeatNo;
import com.binar.flyket.repository.SeatRepository;
import com.binar.flyket.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;

    public SeatServiceImpl(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Override
    public boolean addSeat(SeatDTO seatDTO) {
        Optional<Seat> seat = seatRepository.findById(seatDTO.getId());
        if(seat.isEmpty()) {
            Seat seatModel = new Seat();
            seatModel.setId(seatDTO.getId());
            seatModel.setIsAvailable(seatDTO.getIsAvailable());
            seatRepository.save(seatModel);
            return true;
        }
        throw FlyketException.throwException(ExceptionType.DUPLICATE_ENTITY, HttpStatus.CONFLICT, Constants.ALREADY_EXIST_MSG);
    }

    @Override
    public List<SeatDTO> addSeats(List<SeatDTO> seatDTO) {
        List<Seat> seats = seatDTO.stream()
                .map(seat -> {
                    Seat seatModel = new Seat();
                    seatModel.setId(seat.getId());
                    seatModel.setIsAvailable(seat.getIsAvailable());
                    return seatModel;
                }).toList();
        seatRepository.saveAll(seats);
        return seatDTO;
    }

    @Override
    public SeatDTO deleteSeat(SeatNo seatId) {
        Optional<Seat> seat = seatRepository.findById(seatId);
        if(seat.isPresent()) {
            seatRepository.delete(seat.get());
            return SeatMapper.toDto(seat.get());
        }
        throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.COUNTRY_NOT_FOUND);
    }

    @Override
    public SeatDTO updateSeat(SeatNo seatId, SeatDTO seatDTO) {
        Optional<Seat> seat = seatRepository.findById(seatId);
        if(seat.isPresent()) {
            Seat seatModel = new Seat();
            seatModel.setId(seatDTO.getId());
            seatModel.setIsAvailable(seatDTO.getIsAvailable());
            return  SeatMapper.toDto(seatModel);
        }
        throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.COUNTRY_NOT_FOUND);
    }

    @Override
    public SeatDTO getSeatById(SeatNo seatId) {
        Optional<Seat> seat = seatRepository.findById(seatId);
        if(seat.isPresent())
            return SeatMapper.toDto(seat.get());
        throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.COUNTRY_NOT_FOUND);
    }

    @Override
    public List<SeatDTO> getSeats() {
        return seatRepository.findAll().stream()
                .map(SeatMapper::toDto).toList();
    }
}
