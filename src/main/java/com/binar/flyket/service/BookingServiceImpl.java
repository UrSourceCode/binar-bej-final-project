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

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BookingServiceImpl implements BookingService {

    private final SeatDetailRepository seatDetailRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final FlightScheduleRepository flightScheduleRepository;
    private final BookingRepository bookingRepository;

    public BookingServiceImpl(FlightScheduleRepository flightScheduleRepository,
                              UserRepository userRepository,
                              SeatDetailRepository seatDetailRepository, TicketRepository ticketRepository,
                              PaymentMethodRepository paymentMethodRepository, BookingRepository bookingRepository) {
        this.flightScheduleRepository = flightScheduleRepository;
        this.userRepository = userRepository;
        this.seatDetailRepository = seatDetailRepository;
        this.ticketRepository = ticketRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    @Override
    public BookingResponse addBooking(String userId, BookingRequest request) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty())
            throw new FlyketException.EntityNotFoundException(HttpStatus.NOT_FOUND, "User with id " + Constants.NOT_FOUND_MSG);

        Optional<FlightSchedule> schedule = flightScheduleRepository.findById(request.getScheduleId());
        if(schedule.isEmpty())
            throw new FlyketException.EntityNotFoundException(HttpStatus.NOT_FOUND, "Flight Schedule with id " + Constants.NOT_FOUND_MSG);

        String[] uniqueId = UUID.randomUUID().toString().toUpperCase().split("-");
        String bookingId = "bk-" +  uniqueId[0] + uniqueId[1];
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
            passengerTicket(passengerRequest, schedule.get(), bookingId);
        }

        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setBookingId(bookingId);
        bookingResponse.setBookingStatus(BookingStatus.ACTIVE);
        bookingResponse.setDate(LocalDateTime.now());
        bookingResponse.setName(user.get().getFirstName() + " " + user.get().getLastName());
        bookingResponse.setEmail(user.get().getEmail());

        return bookingResponse;
    }

    private void passengerTicket(PassengerRequest passengerRequest, FlightSchedule flightSchedule, String bookingId) {
        AircraftDetail aircraftDetail = flightSchedule.getAircraftDetail();
        String row = passengerRequest.getSeatNo().getSeatRow().toUpperCase();
        Integer seatNo = passengerRequest.getSeatNo().getSeatNo();

        Optional<SeatDetail> seatDetail = seatDetailRepository.findSeatDetail(aircraftDetail.getId(), row, seatNo);

        Optional<Booking> booking = bookingRepository.findById(bookingId);

        String[] uniqueId = UUID.randomUUID().toString().toUpperCase().split("-");
        String ticketId = "ticket-" + uniqueId[0] + uniqueId[1];

        Ticket ticket = new Ticket();
        ticket.setId(ticketId);
        ticket.setBooking(booking.get());
        ticket.setPassengerTitle(passengerRequest.getTitle());
        ticket.setPassengerName(passengerRequest.getName());
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());
        ticket.setFlightSchedule(flightSchedule);
        ticket.setStatus(false);
        ticket.setSeatDetail(seatDetail.get());

        ticketRepository.save(ticket);
    }

    @Override
    public Boolean validateBooking(String userId, String bookingId) {
        return null;
    }
}
