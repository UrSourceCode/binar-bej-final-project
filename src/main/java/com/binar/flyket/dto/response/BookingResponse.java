package com.binar.flyket.dto.response;

import com.binar.flyket.model.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
public class BookingResponse {
    private String bookingId;
    private String userName;
    private String userEmail;
    private LocalDateTime date;
    private BookingStatus bookingStatus;
}
