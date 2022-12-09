package com.binar.flyket.dto.model;


import com.binar.flyket.model.AircraftClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SeatDetailDTO {
    private String seatId;
    private String aircraftDetailId;
    private String row;
    private Integer no;
    private AircraftClass aircraftClass;
    private Integer maxCabin;
    private Integer maxBaggage;

}
