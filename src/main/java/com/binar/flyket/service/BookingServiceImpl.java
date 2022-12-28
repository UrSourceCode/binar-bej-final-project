package com.binar.flyket.service;

import com.binar.flyket.dto.model.AvailableSeatDTO;
import com.binar.flyket.dto.model.BookingDTO;
import com.binar.flyket.dto.model.BookingDetailDTO;
import com.binar.flyket.dto.model.BookingValidateDTO;
import com.binar.flyket.dto.request.BookingRequest;
import com.binar.flyket.dto.request.PassengerRequest;
import com.binar.flyket.dto.request.PaymentRequest;
import com.binar.flyket.dto.response.BookingResponse;
import com.binar.flyket.dto.response.BookingStatusResponse;
import com.binar.flyket.dto.response.PaymentResponse;
import com.binar.flyket.exception.ExceptionType;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.model.*;
import com.binar.flyket.model.user.User;
import com.binar.flyket.repository.*;
import com.binar.flyket.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
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
    private final NotificationRepository notificationRepository;

    public BookingServiceImpl(FlightScheduleRepository flightScheduleRepository,
                              UserRepository userRepository,
                              SeatDetailRepository seatDetailRepository,
                              TicketRepository ticketRepository,
                              PaymentMethodRepository paymentMethodRepository,
                              BookingRepository bookingRepository,
                              NotificationRepository notificationRepository) {
        this.flightScheduleRepository = flightScheduleRepository;
        this.userRepository = userRepository;
        this.seatDetailRepository = seatDetailRepository;
        this.ticketRepository = ticketRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.bookingRepository = bookingRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public BookingDetailDTO getBookingDetail(String bookingId) {
        Optional<BookingDetailDTO> bookingDetailDTO = bookingRepository.getBookingDetail(bookingId);
        if(bookingDetailDTO.isEmpty())
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.NOT_FOUND_MSG);
        return bookingDetailDTO.get();
    }

    @Transactional
    @Override
    public BookingResponse addBooking(String userId, BookingRequest request) {

        LOGGER.info("~ Start booking ~");

        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty())
            throw new FlyketException.EntityNotFoundException(HttpStatus.NOT_FOUND, "User with id " + userId + " " + Constants.NOT_FOUND_MSG);

        Optional<FlightSchedule> schedule = flightScheduleRepository.findById(request.getScheduleId());
        if(schedule.isEmpty())
            throw new FlyketException.EntityNotFoundException(HttpStatus.NOT_FOUND, "Flight Schedule " + Constants.NOT_FOUND_MSG);

        long currentTime = System.currentTimeMillis() + 1800000L;

        String[] uniqueId = UUID.randomUUID().toString().toUpperCase().split("-");
        String bookingId = "bk-" +  uniqueId[0] + uniqueId[1];
        Booking booking = new Booking();
        booking.setId(bookingId);
        booking.setUser(user.get());
        booking.setExpiredTime(currentTime);
        booking.setAmount(request.getAmount());
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUpdatedAt(LocalDateTime.now());
        booking.setFlightSchedule(schedule.get());
        booking.setTotalPassenger(request.getTotalPassenger());
        booking.setBookingStatus(BookingStatus.ACTIVE);

        bookingRepository.save(booking);

        for(PassengerRequest passengerRequest : request.getPassengerRequests()) {
            passengerTicket(passengerRequest, schedule.get(), booking);
        }

        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setBookingId(bookingId);
        bookingResponse.setBookingStatus(BookingStatus.ACTIVE);
        bookingResponse.setDate(LocalDateTime.now());
        bookingResponse.setName(user.get().getFirstName() + " " + user.get().getLastName());
        bookingResponse.setEmail(user.get().getEmail());


        notificationRepository.save(buildNotification("Pesananmu berhasil", "" ,
                "Ayo, segera lakukan pembayaran!!", false, user.get()));

        LOGGER.info("~ Finish booking ~");

        return bookingResponse;
    }

    private void passengerTicket(PassengerRequest passengerRequest, FlightSchedule flightSchedule, Booking booking) {
        if(passengerRequest.getSeatNo() == null)
            throw new FlyketException.InputIsEmptyException(HttpStatus.NOT_ACCEPTABLE, "Seat no : " + Constants.EMPTY_MSG);

        Optional<SeatDetail> seatDetail = seatDetailRepository.findById(passengerRequest.getSeatNo().toUpperCase());
        if(seatDetail.isEmpty())
            throw new FlyketException.EntityNotFoundException(HttpStatus.NOT_FOUND, "Seat no " + Constants.NOT_FOUND_MSG);

        String[] uniqueId = UUID.randomUUID().toString().toUpperCase().split("-");
        String ticketId = "ticket-" + uniqueId[0] + uniqueId[1];

        Ticket ticket = new Ticket();
        ticket.setId(ticketId);
        ticket.setBooking(booking);
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
        LOGGER.info("~ start validate Booking by ADMIN ~");
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty())
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, "User with id : " + Constants.NOT_FOUND_MSG);

        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if(booking.isEmpty())
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, "Booking with Id : " + Constants.NOT_FOUND_MSG);

        checkStatusBooking(booking.get());

        if(booking.get().getPaymentMethod() == null)
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, "Payment " + Constants.NOT_FOUND_MSG);

        List<Ticket> tickets = ticketRepository.findBooking(bookingId);
        if(tickets.isEmpty())
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, "Ticket " + Constants.NOT_FOUND_MSG);

        for(Ticket tc : tickets) processTicket(tc);

        Booking bookingModel = booking.get();
        bookingModel.setUpdatedAt(LocalDateTime.now());
        bookingModel.setBookingStatus(BookingStatus.COMPLETED);
        bookingRepository.save(bookingModel);

        notificationRepository.save(buildNotification("Validasi Admin", "",
                "Pembayaran kamu sudah divalidasi oleh ADMIN. Semoga perjalanan mu menyenangkan :)", false, user.get()));

        LOGGER.info("~ finish validate by ADMIN ~");

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
        bookingModel.setBookingStatus(BookingStatus.WAITING);
        bookingModel.setUpdatedAt(LocalDateTime.now());
        bookingRepository.save(bookingModel);

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setPaymentName(paymentMethod.get().getName());
        paymentResponse.setPrice(bookingModel.getAmount());
        paymentResponse.setBookingId(request.getBookingId());
        paymentResponse.setEmail(user.get().getEmail());

        notificationRepository.save(buildNotification("Pembayaran berhasil", "",
                "Terima kasih telah melakukan pembayaran. Metode pembayaran melalui " + paymentMethod.get().getName() +"\n" +
                "Booking Id : " + booking.get().getId() + ".\n" +
                "Total : " + booking.get().getAmount(), false, user.get()));

        LOGGER.info("~ Finish Payment ~");

        return paymentResponse;
    }

    @Override
    public List<AvailableSeatDTO> showSeat(String scheduleId) {
        Optional<FlightSchedule> flightSchedule = flightScheduleRepository.findById(scheduleId);
        if(flightSchedule.isEmpty())
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.NOT_FOUND_MSG);
        return ticketRepository.findAvailableSeat(scheduleId);
    }

    @Override

    public List<BookingValidateDTO> validateBookingList(Pageable pageable) {
        return bookingRepository.validateBookingList(BookingStatus.WAITING, pageable).getContent();
    }

    @Override
    public List<BookingDTO> findByStatus(String status, Pageable pageable) {
        BookingStatus bookingStatus = BookingStatus.getStatus(status);
        return bookingRepository.findBookingStatus(bookingStatus, pageable).getContent();
    }

    public BookingStatusResponse bookingStatus(String bookingId) {
        Optional<Booking> booking = bookingRepository.checkStatus(bookingId);
        if(booking.isEmpty())
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.NOT_FOUND_MSG);
        BookingStatusResponse response = new BookingStatusResponse();
        response.setBookingId(bookingId);
        response.setBookingStatus(booking.get().getBookingStatus());
        response.setIsPaid(booking.get().getBookingStatus() == BookingStatus.WAITING
                        || booking.get().getBookingStatus() == BookingStatus.COMPLETED);
        return response;

    }

    private void checkStatusBooking(Booking bookingModel) {
        switch (bookingModel.getBookingStatus()) {
            case EXPIRED -> throw FlyketException.throwException(ExceptionType.BOOKING_EXPIRED, HttpStatus.NOT_ACCEPTABLE, "Booking expired!");
        }
    }

    private Notification buildNotification(String title,
                                           String imgUrl,
                                           String content,
                                           Boolean status,
                                           User user) {
        String[] uniqueId =  UUID.randomUUID().toString().toUpperCase().split("-");
        String notificationId = "notification-" + uniqueId[0] + uniqueId[1];
        Notification notification = new Notification();
        notification.setId(notificationId);
        notification.setTitle(title);
        notification.setImgUrl(imgUrl);
        notification.setContent(content);
        notification.setUser(user);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setUpdatedAt(LocalDateTime.now());
        notification.setIsRead(status);
        return notification;
    }
}
