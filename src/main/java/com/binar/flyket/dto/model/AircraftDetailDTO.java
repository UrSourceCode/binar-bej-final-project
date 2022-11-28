package com.binar.flyket.dto.model;

import com.binar.flyket.model.AircraftClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AircraftDetailDTO {
    private String id;
    private AircraftClass aircraftClass;
    private BigDecimal price;
    private Integer maxBaggage;
    private Integer maxCabin;
    private String aircraftType;
}
