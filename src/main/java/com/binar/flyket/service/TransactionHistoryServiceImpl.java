package com.binar.flyket.service;

import com.binar.flyket.dto.model.BookingDTO;
import com.binar.flyket.dto.model.BookingHistoryDTO;
import com.binar.flyket.dto.model.MyOrderDTO;
import com.binar.flyket.dto.model.MyOrderDetailDTO;
import com.binar.flyket.exception.ExceptionType;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.model.Booking;
import com.binar.flyket.model.user.User;
import com.binar.flyket.repository.*;
import com.binar.flyket.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionHistoryServiceImpl implements TransactionHistoryService {

    private Logger LOGGER = LoggerFactory.getLogger(TransactionHistoryServiceImpl.class);

    private UserRepository userRepository;
    private BookingRepository bookingRepository;
    private FlightScheduleRepository flightScheduleRepository;
    private PaymentMethodRepository paymentMethodRepository;
    private TicketRepository ticketRepository;

    public TransactionHistoryServiceImpl(UserRepository userRepository,
                                         BookingRepository bookingRepository,
                                         FlightScheduleRepository flightScheduleRepository,
                                         PaymentMethodRepository paymentMethodRepository,
                                         TicketRepository ticketRepository) {
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.flightScheduleRepository = flightScheduleRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public List<MyOrderDTO> getRecentOrder(String userId, Pageable pageable) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty())
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.NOT_FOUND_MSG);

        return bookingRepository.getRecentOrderByUser(userId, pageable).getContent();
    }

    @Override
    public MyOrderDetailDTO getRecentOrderDetail(String bookingId) {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if(booking.isEmpty())
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.NOT_FOUND_MSG);
        MyOrderDetailDTO myOrderDetailDTO = new MyOrderDetailDTO();
        myOrderDetailDTO.setBookingId(bookingId);
        myOrderDetailDTO.setPassengerTicketLists(ticketRepository.getRecentOrderDetail(bookingId));
        return myOrderDetailDTO;
    }

    @Override
    public List<BookingHistoryDTO> getAllBookingHistory(Pageable pageable) {
        return bookingRepository.findAllBooking(pageable).getContent();
    }
}
