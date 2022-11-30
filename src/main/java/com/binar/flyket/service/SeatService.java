package com.binar.flyket.service;

import com.binar.flyket.dto.model.SeatDTO;

import java.util.List;

public interface SeatService {

    boolean addSeat(SeatDTO seatDTO);

    List<SeatDTO> addSeats(List<SeatDTO> seatDTO);

    SeatDTO deleteSeat(Integer seatId);

    SeatDTO updateSeat(Integer seatId, SeatDTO seatDTO);

    SeatDTO getSeatById(Integer seatId);

    List<SeatDTO> getSeats();
}
