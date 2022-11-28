package com.binar.flyket.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRouteRequest {
    private String originAirportCode;
    private String destinationAirportCode;
    private Integer hours;
    private Integer minutes;
}
