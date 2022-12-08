package com.binar.flyket.service;

import com.binar.flyket.dto.request.BookingRequest;
import com.binar.flyket.dto.response.BookingResponse;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.model.FlightSchedule;
import com.binar.flyket.model.user.User;
import com.binar.flyket.repository.*;
import com.binar.flyket.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {

    private final SeatRepository seatRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final SeatDetailRepository seatDetailRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final FlightScheduleRepository flightScheduleRepository;

    public BookingServiceImpl(FlightScheduleRepository flightScheduleRepository,
                              UserRepository userRepository,
                              SeatRepository seatRepository,
                              TicketRepository ticketRepository,
                              SeatDetailRepository seatDetailRepository,
                              PaymentMethodRepository paymentMethodRepository) {
        this.flightScheduleRepository = flightScheduleRepository;
        this.userRepository = userRepository;
        this.seatRepository = seatRepository;
        this.ticketRepository = ticketRepository;
        this.seatDetailRepository = seatDetailRepository;
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Override
    public BookingResponse addBooking(String userId, BookingRequest request) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty())
            throw new FlyketException.EntityNotFoundException(HttpStatus.NOT_FOUND, "User with id " + Constants.NOT_FOUND_MSG);

        Optional<FlightSchedule> schedule = flightScheduleRepository.findById(request.getScheduleId());
        if(schedule.isEmpty())
            throw new FlyketException.EntityNotFoundException(HttpStatus.NOT_FOUND, "Flight Schedule with id " + Constants.NOT_FOUND_MSG);






        return null;
    }

    private void insertPassenger(BookingRequest request) {
        String uid = UUID.randomUUID().toString();

    }

    private Boolean isSeatAvailable(String scId, ) {

    }


    @Override
    public Boolean validateBooking(String userId, String bookingId) {
        return null;
    }
}
