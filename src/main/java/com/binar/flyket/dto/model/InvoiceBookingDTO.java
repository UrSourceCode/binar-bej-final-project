package com.binar.flyket.dto.model;

import com.binar.flyket.model.AircraftClass;
import com.binar.flyket.model.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceBookingDTO {
    private String orderId;
    private String flightId;
    private String cstName;
    private String phoneNumber;
    private String email;
    private BookingStatus status;
    private String paymentName;
    private BigDecimal amount;
    private String airportName;
    private AircraftClass seatClass;
    private LocalDateTime purchaseAt;
}
