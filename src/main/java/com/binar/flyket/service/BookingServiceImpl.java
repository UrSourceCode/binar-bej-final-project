package com.binar.flyket.service;

import com.binar.flyket.dto.request.BookingRequest;
import com.binar.flyket.dto.request.PassengerRequest;
import com.binar.flyket.dto.response.BookingResponse;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.model.*;
import com.binar.flyket.model.user.User;
import com.binar.flyket.repository.*;
import com.binar.flyket.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class BookingServiceImpl implements BookingService {

    private final AvailableSeatRepository availableSeatRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final FlightScheduleRepository flightScheduleRepository;
    private final BookingRepository bookingRepository;

    public BookingServiceImpl(FlightScheduleRepository flightScheduleRepository,
                              UserRepository userRepository,
                              AvailableSeatRepository availableSeatRepository, TicketRepository ticketRepository,
                              PaymentMethodRepository paymentMethodRepository, BookingRepository bookingRepository) {
        this.flightScheduleRepository = flightScheduleRepository;
        this.userRepository = userRepository;
        this.availableSeatRepository = availableSeatRepository;
        this.ticketRepository = ticketRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public BookingResponse addBooking(String userId, BookingRequest request) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty())
            throw new FlyketException.EntityNotFoundException(HttpStatus.NOT_FOUND, "User with id " + Constants.NOT_FOUND_MSG);

        Optional<FlightSchedule> schedule = flightScheduleRepository.findById(request.getScheduleId());
        if(schedule.isEmpty())
            throw new FlyketException.EntityNotFoundException(HttpStatus.NOT_FOUND, "Flight Schedule with id " + Constants.NOT_FOUND_MSG);


        String bookingId = "bk-" + UUID.randomUUID().toString().split("-")[0].toUpperCase();
        Booking booking = new Booking();
        booking.setId(bookingId);
        booking.setExpiredTime(System.currentTimeMillis() + 1800000L);
        booking.setAmount(request.getAmount());
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUpdatedAt(LocalDateTime.now());
        booking.setFlightSchedule(schedule.get());
        booking.setBookingStatus(BookingStatus.ACTIVE);

        bookingRepository.save(booking);

        for(PassengerRequest passengerRequest : request.getPassengerRequests()) {
            passengerTicket(passengerRequest, bookingId);
        }

        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setBookingId(bookingId);
        bookingResponse.setBookingStatus(BookingStatus.ACTIVE);
        bookingResponse.setDate(LocalDateTime.now());
        bookingResponse.setName(user.get().getFirstName() + " " + user.get().getLastName());
        bookingResponse.setUserEmail(user.get().getEmail());

        return bookingResponse;
    }

    private void passengerTicket(PassengerRequest passengerRequest, String bookingId) {

    }

    @Override
    public Boolean validateBooking(String userId, String bookingId) {
        return null;
    }
}
