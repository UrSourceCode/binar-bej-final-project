package com.binar.flyket.dto.model;


import com.binar.flyket.model.AircraftClass;
import com.binar.flyket.model.Airport;
import com.binar.flyket.model.BookingStatus;
import com.binar.flyket.model.PaymentMethod;
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
    private BigDecimal totalPayment;
}
