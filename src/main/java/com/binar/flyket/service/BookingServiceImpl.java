package com.binar.flyket.service;

import com.binar.flyket.dto.request.BookingRequest;
import com.binar.flyket.dto.request.PassengerRequest;
import com.binar.flyket.dto.request.PaymentRequest;
import com.binar.flyket.dto.response.BookingResponse;
import com.binar.flyket.dto.response.PaymentResponse;
import com.binar.flyket.exception.ExceptionType;
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

        Optional<SeatDetail> seatDetail = seatDetailRepository.findById(passengerRequest.getSeatNo());

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

    @Transactional
    @Override
    public Boolean validateBooking(String userId, String bookingId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty())
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, "User with id : " + Constants.NOT_FOUND_MSG);

        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if(booking.isEmpty())
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, "Booking with Id : " + Constants.NOT_FOUND_MSG);

        List<Ticket> tickets = ticketRepository.findBooking(bookingId);
        if(tickets.isEmpty())
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, "Ticket " + Constants.NOT_FOUND_MSG);

        for(Ticket tc : tickets) {
            processTicket(tc);
        }

        return true;
    }

    private void processTicket(Ticket ticket) {
        ticket.setStatus(true);
        ticketRepository.save(ticket);
    }

    @Transactional
    @Override
    public PaymentResponse setPaymentMethod(PaymentRequest request) {
        Optional<User> user = userRepository.findById(request.getUid());
        if(user.isEmpty()) throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.NOT_FOUND_MSG);

        Optional<Booking> booking = bookingRepository.findById(request.getBookingId());
        if(booking.isEmpty()) throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.NOT_FOUND_MSG);

        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(request.getPaymentId());
        if(paymentMethod.isEmpty()) throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.NOT_FOUND_MSG);

        Booking bookingModel = booking.get();
        bookingModel.setPaymentMethod(paymentMethod.get());

        bookingRepository.save(bookingModel);

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setPaymentName(paymentMethod.get().getName());
        paymentResponse.setPrice(bookingModel.getAmount());
        paymentResponse.setBookingId(request.getBookingId());
        paymentResponse.setEmail(user.get().getEmail());

        return paymentResponse;
    }
}
