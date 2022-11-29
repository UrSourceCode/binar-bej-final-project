package com.binar.flyket.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FlightScheduleRequest {
    private String id;
    @JsonFormat(pattern="HH:mm:ss")
    private LocalTime departureTime;
    @JsonFormat(pattern="HH:mm:ss")
    private LocalTime arrivalTime;
    private LocalDate flightDate;
    private String aircraftDetailId;
    private String routeId;
}
