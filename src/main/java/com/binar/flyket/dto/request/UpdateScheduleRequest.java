package com.binar.flyket.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateScheduleRequest {
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate flightDate;
    private String aircraftDetailId;
    private String flightRouteId;
}
