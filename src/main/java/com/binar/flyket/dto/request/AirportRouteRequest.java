package com.binar.flyket.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AirportRouteRequest {
    @NotNull
    @Size(max = 4)
    private String fromAirport;
    @NotNull
    @Size(max = 4)
    private String toAirport;
    @NotNull
    private Integer hours;
    @NotNull
    private Integer minutes;

}
