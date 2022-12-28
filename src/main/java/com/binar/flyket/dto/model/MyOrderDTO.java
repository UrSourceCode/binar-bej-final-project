package com.binar.flyket.dto.model;


import com.binar.flyket.model.BookingStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String flightId;
    private String bookingId;
    private String userId;
    private String email;
    private String phoneNumber;
    private BigDecimal amount;
    private Integer totalPassenger;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime arrivalTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departureTime;
    private String originAirport;
    private String destinationAirport;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime purchaseCompleteAt;
    private BookingStatus bookingStatus;
    private Integer hours;
    private Integer minutes;
    private BigDecimal price;
}
