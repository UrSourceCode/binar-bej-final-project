package com.binar.flyket.dto.response;

import com.binar.flyket.model.BookingStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class BookingResponse {
    private String bookingId;
    private String name;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    private BookingStatus bookingStatus;
}
