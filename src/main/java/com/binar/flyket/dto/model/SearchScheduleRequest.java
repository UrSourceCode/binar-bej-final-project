package com.binar.flyket.dto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchScheduleRequest {
    private String originAirportId;
    private String destinationAirportId;
    private String aircraftClass;
}
