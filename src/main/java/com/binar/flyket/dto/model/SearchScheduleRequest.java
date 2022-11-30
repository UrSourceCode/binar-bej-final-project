package com.binar.flyket.dto.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchScheduleRequest {
    private String originAirportId;
    private String destinationAirportId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate flightDate;
    private String aircraftClass;
}
