package com.binar.flyket.dto.request;

import com.binar.flyket.model.AircraftClass;
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
    private String userId;
    private String scheduleId;
    private AircraftClass seatClass;
    private BigDecimal amount;
    private List<PassengerRequest> passengerRequests;
    private Long expiredTime;
}
