package com.binar.flyket.service;

import com.binar.flyket.dto.model.AvailableSeatDTO;
import com.binar.flyket.dto.model.BookingDTO;
import com.binar.flyket.dto.model.BookingDetailDTO;
import com.binar.flyket.dto.model.BookingValidateDTO;
import com.binar.flyket.dto.request.BookingRequest;
import com.binar.flyket.dto.request.PaymentRequest;
import com.binar.flyket.dto.response.BookingResponse;
import com.binar.flyket.dto.response.BookingStatusResponse;
import com.binar.flyket.dto.response.PaymentResponse;

import org.springframework.data.domain.Pageable;

import com.binar.flyket.model.BookingStatus;

import java.util.List;

public interface BookingService {

    BookingDetailDTO getBookingDetail(String bookingId);

    BookingResponse addBooking(String userId, BookingRequest request);

    Boolean validateBooking(String userId, String bookingId);

    PaymentResponse setPaymentMethod(PaymentRequest request);

    List<AvailableSeatDTO> showSeat(String scheduleId);


    List<BookingValidateDTO> validateBookingList(Pageable pageable);

    List<BookingDTO> findByStatus(String status, Pageable pageable);


    BookingStatusResponse bookingStatus(String bookingId);

}
