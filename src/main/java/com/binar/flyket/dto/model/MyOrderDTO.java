package com.binar.flyket.dto.model;


import com.binar.flyket.model.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyOrderDTO {
    private String flightScheduleId;
    private Integer totalPassenger;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
    private String originAirport;
    private String destinationAirport;
    private LocalDateTime purchaseCompleteAt;
    private BookingStatus bookingStatus;
    private Integer hours;
    private Integer minutes;
    private BigDecimal price;
}
