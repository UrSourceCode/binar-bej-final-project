package com.binar.flyket.dto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetailDTO {
    private String userId;
    private String originAirportCode;
    private String destinationAirportCode;
    private Integer hours;
    private Integer minutes;
    private String amount;
}
