package com.binar.flyket.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate flightDate;
    @NonNull
    private String aircraftDetailId;
    @NonNull
    private String routeId;
}
