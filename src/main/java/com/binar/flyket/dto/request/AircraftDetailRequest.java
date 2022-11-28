package com.binar.flyket.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AircraftDetailRequest {
    private String aircraftClass;
    private BigDecimal aircraftPrice;
    private Integer aircraftMaxBaggage;
    private Integer aircraftMaxCabin;
    private Integer aircraftNo;

}
