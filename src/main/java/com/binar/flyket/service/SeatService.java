package com.binar.flyket.service;

import com.binar.flyket.dto.model.SeatDTO;
import com.binar.flyket.model.SeatNo;

import java.util.List;

@Deprecated public interface SeatService {

    boolean addSeat(SeatDTO seatDTO);

    List<SeatDTO> addSeats(List<SeatDTO> seatDTO);

    SeatDTO deleteSeat(SeatNo seatId);

    SeatDTO updateSeat(SeatNo seatId, SeatDTO seatDTO);

    SeatDTO getSeatById(SeatNo seatId);

    List<SeatDTO> getSeats();
}
