package com.binar.flyket.dto.response;

import com.binar.flyket.model.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingStatusResponse {
    private String bookingId;
    private BookingStatus bookingStatus;
    private Boolean isPaid;
}
