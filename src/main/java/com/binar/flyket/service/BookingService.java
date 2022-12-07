package com.binar.flyket.service;

import com.binar.flyket.dto.request.BookingRequest;
import com.binar.flyket.dto.response.BookingResponse;

public interface BookingService {

    BookingResponse addBooking(String userId, BookingRequest request);

    Boolean validateBooking(String userId, String bookingId);


    // TODO: Payment
}
