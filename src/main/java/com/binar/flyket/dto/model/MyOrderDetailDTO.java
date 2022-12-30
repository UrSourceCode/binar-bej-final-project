package com.binar.flyket.dto.model;


import com.binar.flyket.model.AircraftClass;
import com.binar.flyket.model.Airport;
import com.binar.flyket.model.BookingStatus;
import com.binar.flyket.model.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyOrderDetailDTO {
    private String bookingId;
    private String flightId;
    private String cstName;
    private String cstEmail;
    private String cstPhoneNumber;
    private BookingStatus bookingStatus;
    private AircraftClass seatClass;
    private String paymentName;
    private String originAirportName;
    private String destinationAirportName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departureTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime arrivalTime;
    private BigDecimal totalPayment;
}
