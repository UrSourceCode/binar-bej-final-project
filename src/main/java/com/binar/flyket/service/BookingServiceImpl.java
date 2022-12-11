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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BookingServiceImpl implements BookingService {

    private Logger LOGGER = LoggerFactory.getLogger(BookingServiceImpl.class);

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

        LOGGER.info("~ Start booking ~");

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
        booking.setUser(user.get());
        booking.setExpiredTime(System.currentTimeMillis() + 1800000L);
        booking.setAmount(request.getAmount());
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUpdatedAt(LocalDateTime.now());
        booking.setFlightSchedule(schedule.get());
        booking.setTotalPassenger(request.getTotalPassenger());
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

        LOGGER.info("~ Finish booking ~");

        return bookingResponse;
    }

    private void passengerTicket(PassengerRequest passengerRequest, FlightSchedule flightSchedule, String bookingId) {

        Optional<SeatDetail> seatDetail = seatDetailRepository.findById(passengerRequest.getSeatNo());
        if(seatDetail.isEmpty())
            throw new FlyketException.EntityNotFoundException(HttpStatus.NOT_FOUND, "Seat no " + Constants.NOT_FOUND_MSG);

        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if(booking.isEmpty())
            throw new FlyketException.EntityNotFoundException(HttpStatus.NOT_FOUND, "Booking with " + bookingId + " " + Constants.NOT_FOUND_MSG);

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
        LOGGER.info("~ Validate Booking by ADMIN ~");
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty())
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, "User with id : " + Constants.NOT_FOUND_MSG);

        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if(booking.isEmpty())
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, "Booking with Id : " + Constants.NOT_FOUND_MSG);

        if(booking.get().getPaymentMethod() == null)
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, "Payment " + Constants.NOT_FOUND_MSG);

        List<Ticket> tickets = ticketRepository.findBooking(bookingId);
        if(tickets.isEmpty())
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, "Ticket " + Constants.NOT_FOUND_MSG);

        checkStatusBooking(booking.get());

        for(Ticket tc : tickets) {
            processTicket(tc);
        }

        Booking bookingModel = booking.get();
        bookingModel.setBookingStatus(BookingStatus.COMPLETED);
        bookingRepository.save(bookingModel);

        LOGGER.info("~ Completed validate by ADMIN ~");

        return true;
    }

    private void processTicket(Ticket ticket) {
        ticket.setStatus(true);
        ticketRepository.save(ticket);
    }

    @Transactional
    @Override
    public PaymentResponse setPaymentMethod(PaymentRequest request) {
        LOGGER.info("~ Start Payment ~");
        Optional<User> user = userRepository.findById(request.getUid());
        if(user.isEmpty()) throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.NOT_FOUND_MSG);

        Optional<Booking> booking = bookingRepository.findById(request.getBookingId());
        if(booking.isEmpty()) throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.NOT_FOUND_MSG);

        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(request.getPaymentId());
        if(paymentMethod.isEmpty()) throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.NOT_FOUND_MSG);

        Booking bookingModel = booking.get();

        checkStatusBooking(bookingModel);

        bookingModel.setPaymentMethod(paymentMethod.get());

        bookingRepository.save(bookingModel);

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setPaymentName(paymentMethod.get().getName());
        paymentResponse.setPrice(bookingModel.getAmount());
        paymentResponse.setBookingId(request.getBookingId());
        paymentResponse.setEmail(user.get().getEmail());

        LOGGER.info("~ Finish Payment ~");

        return paymentResponse;
    }

    private void checkStatusBooking(Booking bookingModel) {
        switch (bookingModel.getBookingStatus()) {
            case EXPIRED -> throw FlyketException.throwException(ExceptionType.BOOKING_EXPIRED, HttpStatus.NOT_ACCEPTABLE, "Booking expired!");
        }
    }
}
