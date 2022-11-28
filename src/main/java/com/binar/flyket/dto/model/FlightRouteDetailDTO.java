package com.binar.flyket.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class FlightRouteDetailDTO {
    private String id;
    private String originAirportCode;
    private String destinationAirportCode;
    private Integer hours;
    private Integer minutes;
}
