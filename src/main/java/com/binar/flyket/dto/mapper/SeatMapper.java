package com.binar.flyket.dto.mapper;

import com.binar.flyket.dto.model.SeatDTO;
import com.binar.flyket.model.Seat;

public class SeatMapper {
    public static SeatDTO toDto(Seat seat) {
        SeatDTO seatDTO = new SeatDTO();
        seatDTO.setId(seat.getId());
        seatDTO.setIsAvailable(seat.getIsAvailable());
        return seatDTO;
    }
}
