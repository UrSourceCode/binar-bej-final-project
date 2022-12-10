package com.binar.flyket.service;

import com.binar.flyket.dto.request.BookingRequest;
import com.binar.flyket.dto.request.PaymentRequest;
import com.binar.flyket.dto.response.BookingResponse;
import com.binar.flyket.dto.response.PaymentResponse;
import com.binar.flyket.model.PaymentMethod;

public interface BookingService {

    BookingResponse addBooking(String userId, BookingRequest request);

    Boolean validateBooking(String userId, String bookingId);

    PaymentResponse setPaymentMethod(PaymentRequest request);
}
