package com.binar.flyket.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    private String scheduleId;
    private String seatClass;
    private Integer totalPassenger;
    private BigDecimal amount;
    private List<PassengerRequest> passengerRequests;
}
