package com.binar.flyket.service;

import com.binar.flyket.dto.model.AvailableSeatDTO;
import com.binar.flyket.dto.model.BookingDTO;
import com.binar.flyket.dto.model.BookingDetailDTO;
import com.binar.flyket.dto.model.BookingValidateDTO;
import com.binar.flyket.dto.request.BookingRequest;
import com.binar.flyket.dto.request.PassengerRequest;
import com.binar.flyket.dto.request.PaymentRequest;
import com.binar.flyket.dummy.*;
import com.binar.flyket.model.*;
import com.binar.flyket.model.user.User;
import com.binar.flyket.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

class BookingServiceImplTest {

    @Mock
    private SeatDetailRepository seatDetailRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private PaymentMethodRepository paymentMethodRepository;

    @Mock
    private FlightScheduleRepository flightScheduleRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetDetailBooking() {
        String[] randId = UUID.randomUUID().toString().toUpperCase().split("-");

        FlightRoute fr = FlightRouteDummies.flightRoute().get(0);

        AircraftDetail acd = AircraftDetailDummies.getAll().get(2);

        FlightSchedule fs = new FlightSchedule();
        fs.setId("sc-" + randId[0] + randId[1]);
        fs.setFlightRoute(fr);
        fs.setAircraftDetail(acd);
        fs.setStatus(Status.ACTIVE);
        fs.setArrivalTime(LocalDateTime.now());
        fs.setDepartureTime(LocalDateTime.now());
        fs.setCreatedAt(LocalDateTime.now());

        String[] uniqueId = UUID.randomUUID().toString().toUpperCase().split("-");
        String bookingId = "bk-" +  uniqueId[0] + uniqueId[1];

        Booking bk = new Booking();
        bk.setId(bookingId);
        bk.setAmount(BigDecimal.valueOf(2_000_000));
        bk.setUser(UserDummies.users().get(0));
        bk.setFlightSchedule(fs);
        bk.setBookingStatus(BookingStatus.ACTIVE);
        bk.setCreatedAt(LocalDateTime.now());
        bk.setUpdatedAt(LocalDateTime.now());
        bk.setExpiredTime(System.currentTimeMillis() + 1800000L);
        bk.setPaymentMethod(PaymentDummies.paymentMethods().get(0));
        bk.setTotalPassenger(2);

        BookingDetailDTO dto = new BookingDetailDTO();
        dto.setBookingId(bookingId);
        dto.setMinutes(bk.getFlightSchedule().getFlightRoute().getMinutes());
        dto.setHours(bk.getFlightSchedule().getFlightRoute().getHours());
        dto.setAmount(bk.getAmount());
        dto.setOriginAirportCode(bk.getFlightSchedule()
                .getFlightRoute()
                .getFromAirport()
                .IATACode);
        dto.setDestinationAirportCode(bk.getFlightSchedule()
                .getFlightRoute()
                .getToAirport()
                .IATACode);

        Mockito.when(bookingRepository.getBookingDetail(bookingId))
                .thenReturn(Optional.of(dto));

        var actualValue = bookingService.getBookingDetail(bookingId);
        var expectedBkId = bookingId;
        var expectedMinutes = bk.getFlightSchedule().getFlightRoute().getMinutes();
        var expectedHours = bk.getFlightSchedule().getFlightRoute().getHours();
        var expectedAmount = bk.getAmount();
        var expectedOriginAirportCode = bk.getFlightSchedule()
                .getFlightRoute()
                .getFromAirport()
                .IATACode;
        var expectedDestinationAirportCode = bk.getFlightSchedule()
                .getFlightRoute()
                .getToAirport()
                .IATACode;

        Assertions.assertNotNull(actualValue);
        Assertions.assertEquals(expectedBkId, actualValue.getBookingId());
        Assertions.assertEquals(expectedHours, actualValue.getHours());
        Assertions.assertEquals(expectedAmount, actualValue.getAmount());
        Assertions.assertEquals(expectedMinutes, actualValue.getMinutes());
        Assertions.assertEquals(expectedDestinationAirportCode, actualValue.getDestinationAirportCode());
        Assertions.assertEquals(expectedOriginAirportCode, actualValue.getOriginAirportCode());
    }

    @Test
    void testAddBooking() {
        PassengerRequest passenger1 = new PassengerRequest();
        passenger1.setName("Riswan Agam");
        passenger1.setTitle("Mr");
        passenger1.setSeatNo("1A");

        List<PassengerRequest> passengerRequests = List.of(passenger1);

        String[] randId = UUID.randomUUID().toString().toUpperCase().split("-");

        FlightRoute fr = FlightRouteDummies.flightRoute().get(0);

        AircraftDetail acd = AircraftDetailDummies.getAll().get(2);

        FlightSchedule fs = new FlightSchedule();
        fs.setId("sc-" + randId[0] + randId[1]);
        fs.setFlightRoute(fr);
        fs.setAircraftDetail(acd);
        fs.setStatus(Status.ACTIVE);
        fs.setArrivalTime(LocalDateTime.now());
        fs.setDepartureTime(LocalDateTime.now());
        fs.setCreatedAt(LocalDateTime.now());

        User usr = UserDummies.users().get(0);

        BookingRequest request = new BookingRequest();
        request.setAmount(BigDecimal.valueOf(2_000_000));
        request.setScheduleId(fs.getId());
        request.setTotalPassenger(2);
        request.setSeatClass("ECONOMY");
        request.setPassengerRequests(passengerRequests);

        String[] uniqueId = UUID.randomUUID().toString().toUpperCase().split("-");
        String bookingId = "bk-" +  uniqueId[0] + uniqueId[1];
        Booking bk = new Booking();
        bk.setId(bookingId);
        bk.setAmount(BigDecimal.valueOf(2_000_000));
        bk.setUser(UserDummies.users().get(0));
        bk.setFlightSchedule(fs);
        bk.setBookingStatus(BookingStatus.ACTIVE);
        bk.setCreatedAt(LocalDateTime.now());
        bk.setUpdatedAt(LocalDateTime.now());
        bk.setExpiredTime(System.currentTimeMillis() + 1800000L);
        bk.setPaymentMethod(PaymentDummies.paymentMethods().get(0));
        bk.setTotalPassenger(2);

        Mockito.when(userRepository.findById(usr.getId()))
                .thenReturn(Optional.of(usr));
        Mockito.when(flightScheduleRepository.findById(fs.getId()))
                .thenReturn(Optional.of(fs));
        Mockito.when(bookingRepository.save(bk))
                .thenReturn(bk);

        String[] randTcId = UUID.randomUUID().toString().toUpperCase().split("-");
        String ticketId = "ticket-" + randTcId[0] + randTcId[1];
        Ticket tc = new Ticket();
        tc.setId(ticketId);
        tc.setBooking(bk);
        tc.setStatus(false);
        tc.setPassengerTitle(passengerRequests.get(0).getTitle());
        tc.setPassengerName(passengerRequests.get(0).getName());
        tc.setSeatDetail(SeatDetailDummies.findSeatById(passenger1.getSeatNo()));
        tc.setFlightSchedule(fs);
        tc.setUpdatedAt(LocalDateTime.now());
        tc.setCreatedAt(LocalDateTime.now());

        Mockito.when(seatDetailRepository.findById(passengerRequests.get(0).getSeatNo()))
                .thenReturn(Optional.of(SeatDetailDummies.seatDetails().get(0)));
        Mockito.when(ticketRepository.save(tc))
                .thenReturn(tc);
        Mockito.when(notificationRepository.save(NotificationDummies.notificationList().get(0)))
                .thenReturn(NotificationDummies.notificationList().get(0));

        var actualValue = bookingService.addBooking(usr.getId(), request);
        var expectedName = usr.getFirstName()
                .concat(" ")
                .concat(usr.getLastName());
        var expectedBookingStatus = BookingStatus.ACTIVE;

        Assertions.assertNotNull(actualValue);
        Assertions.assertEquals(expectedBookingStatus, actualValue.getBookingStatus());
        Assertions.assertEquals(expectedName, actualValue.getName());
    }

    @Test
    void testValidateBooking() {
        PassengerRequest passenger1 = new PassengerRequest();
        passenger1.setName("Riswan Agam");
        passenger1.setTitle("Mr");
        passenger1.setSeatNo("1A");

        List<PassengerRequest> passengerRequests = List.of(passenger1);

        String[] randId = UUID.randomUUID().toString().toUpperCase().split("-");

        FlightRoute fr = FlightRouteDummies.flightRoute().get(0);

        AircraftDetail acd = AircraftDetailDummies.getAll().get(2);

        FlightSchedule fs = new FlightSchedule();
        fs.setId("sc-" + randId[0] + randId[1]);
        fs.setFlightRoute(fr);
        fs.setAircraftDetail(acd);
        fs.setStatus(Status.ACTIVE);
        fs.setArrivalTime(LocalDateTime.now());
        fs.setDepartureTime(LocalDateTime.now());
        fs.setCreatedAt(LocalDateTime.now());

        User usr = UserDummies.users().get(0);

        BookingRequest request = new BookingRequest();
        request.setAmount(BigDecimal.valueOf(2_000_000));
        request.setScheduleId(fs.getId());
        request.setTotalPassenger(2);
        request.setSeatClass("ECONOMY");
        request.setPassengerRequests(passengerRequests);

        String[] uniqueId = UUID.randomUUID().toString().toUpperCase().split("-");
        String bookingId = "bk-" +  uniqueId[0] + uniqueId[1];
        Booking bk = new Booking();
        bk.setId(bookingId);
        bk.setAmount(BigDecimal.valueOf(2_000_000));
        bk.setUser(UserDummies.users().get(0));
        bk.setFlightSchedule(fs);
        bk.setBookingStatus(BookingStatus.ACTIVE);
        bk.setCreatedAt(LocalDateTime.now());
        bk.setUpdatedAt(LocalDateTime.now());
        bk.setExpiredTime(System.currentTimeMillis() + 1800000L);
        bk.setPaymentMethod(PaymentDummies.paymentMethods().get(0));
        bk.setTotalPassenger(2);

        String[] randTcId = UUID.randomUUID().toString().toUpperCase().split("-");
        String ticketId = "ticket-" + randTcId[0] + randTcId[1];
        Ticket tc = new Ticket();
        tc.setId(ticketId);
        tc.setBooking(bk);
        tc.setStatus(false);
        tc.setPassengerTitle(passengerRequests.get(0).getTitle());
        tc.setPassengerName(passengerRequests.get(0).getName());
        tc.setSeatDetail(SeatDetailDummies.findSeatById(passenger1.getSeatNo()));
        tc.setFlightSchedule(fs);
        tc.setUpdatedAt(LocalDateTime.now());
        tc.setCreatedAt(LocalDateTime.now());

        List<Ticket> tickets = List.of(tc);

        Mockito.when(userRepository.findById(usr.getId()))
                .thenReturn(Optional.of(usr));
        Mockito.when(bookingRepository.findById(bookingId))
                .thenReturn(Optional.of(bk));
        Mockito.when(ticketRepository.findBooking(bookingId))
                .thenReturn(tickets);
        Mockito.when(bookingRepository.save(bk))
                .thenReturn(bk);
        Mockito.when(notificationRepository.save(NotificationDummies.notificationList().get(0)))
                .thenReturn(NotificationDummies.notificationList().get(0));

        var actualValue = bookingService.validateBooking(usr.getId(), bookingId);
        var expectedValue = true;

        Assertions.assertNotNull(actualValue);
        Assertions.assertEquals(expectedValue, actualValue);

    }

    @Test
    void testSetPaymentMethod() {
        FlightRoute fr = FlightRouteDummies.flightRoute().get(0);

        AircraftDetail acd = AircraftDetailDummies.getAll().get(2);

        String[] randId = UUID.randomUUID().toString().toUpperCase().split("-");
        FlightSchedule fs = new FlightSchedule();
        fs.setId("sc-" + randId[0] + randId[1]);
        fs.setFlightRoute(fr);
        fs.setAircraftDetail(acd);
        fs.setStatus(Status.ACTIVE);
        fs.setArrivalTime(LocalDateTime.now());
        fs.setDepartureTime(LocalDateTime.now());
        fs.setCreatedAt(LocalDateTime.now());

        String[] uniqueId = UUID.randomUUID().toString().toUpperCase().split("-");
        String bookingId = "bk-" +  uniqueId[0] + uniqueId[1];
        Booking bk = new Booking();
        bk.setId(bookingId);
        bk.setAmount(BigDecimal.valueOf(2_000_000));
        bk.setUser(UserDummies.users().get(0));
        bk.setFlightSchedule(fs);
        bk.setBookingStatus(BookingStatus.ACTIVE);
        bk.setCreatedAt(LocalDateTime.now());
        bk.setUpdatedAt(LocalDateTime.now());
        bk.setExpiredTime(System.currentTimeMillis() + 1800000L);
        bk.setPaymentMethod(PaymentDummies.paymentMethods().get(0));
        bk.setTotalPassenger(2);

        User usr = UserDummies.users().get(0);

        PaymentRequest request = new PaymentRequest();
        request.setBookingId(bookingId);
        request.setUid(usr.getId());
        request.setPaymentId(PaymentDummies.paymentMethods().get(0).getId());

        Mockito.when(userRepository.findById(usr.getId()))
                .thenReturn(Optional.of(usr));
        Mockito.when(bookingRepository.save(bk))
                .thenReturn(bk);
        Mockito.when(bookingRepository.findById(request.getBookingId()))
                .thenReturn(Optional.of(bk));
        Mockito.when(paymentMethodRepository.findById(request.getPaymentId()))
                .thenReturn(Optional.of(PaymentDummies.paymentMethods().get(0)));

        var actualValue = bookingService.setPaymentMethod(request);
        var expectedPaymentName = PaymentDummies.paymentMethods().get(0).getName();
        var expectedPrice = bk.getAmount();
        var expectedUserEmail = usr.getEmail();

        Assertions.assertNotNull(actualValue);
        Assertions.assertEquals(expectedPaymentName, actualValue.getPaymentName());
        Assertions.assertEquals(expectedUserEmail, actualValue.getEmail());
        Assertions.assertEquals(expectedPrice, actualValue.getPrice());
    }

    @Test
    void testShowSeat() {
        FlightRoute fr = FlightRouteDummies.flightRoute().get(0);

        AircraftDetail acd = AircraftDetailDummies.getAll().get(2);

        String[] randId = UUID.randomUUID().toString().toUpperCase().split("-");
        FlightSchedule fs = new FlightSchedule();
        fs.setId("sc-" + randId[0] + randId[1]);
        fs.setFlightRoute(fr);
        fs.setAircraftDetail(acd);
        fs.setStatus(Status.ACTIVE);
        fs.setArrivalTime(LocalDateTime.now());
        fs.setDepartureTime(LocalDateTime.now());
        fs.setCreatedAt(LocalDateTime.now());

        AvailableSeatDTO avs1 = new AvailableSeatDTO();
        avs1.setSeatNo("1A");

        AvailableSeatDTO avs2 = new AvailableSeatDTO();
        avs2.setSeatNo("2A");

        List<AvailableSeatDTO> avs = List.of(avs1, avs2);

        Mockito.when(flightScheduleRepository.findById(fs.getId()))
                .thenReturn(Optional.of(fs));
        Mockito.when(ticketRepository.findAvailableSeat(fs.getId()))
                .thenReturn(avs);

        var actualValue = bookingService.showSeat(fs.getId());
        var expectedSize = 2;

        Assertions.assertNotNull(actualValue);
        Assertions.assertEquals(expectedSize, actualValue.size());
    }

    @Test
    void testValidateBookingList() {
        String[] randId = UUID.randomUUID().toString().toUpperCase().split("-");

        FlightRoute fr = FlightRouteDummies.flightRoute().get(0);

        AircraftDetail acd = AircraftDetailDummies.getAll().get(2);

        FlightSchedule fs = new FlightSchedule();
        fs.setId("sc-" + randId[0] + randId[1]);
        fs.setFlightRoute(fr);
        fs.setAircraftDetail(acd);
        fs.setStatus(Status.ACTIVE);
        fs.setArrivalTime(LocalDateTime.now());
        fs.setDepartureTime(LocalDateTime.now());
        fs.setCreatedAt(LocalDateTime.now());

        String[] uniqueId = UUID.randomUUID().toString().toUpperCase().split("-");
        String bookingId = "bk-" +  uniqueId[0] + uniqueId[1];
        Booking bk = new Booking();
        bk.setId(bookingId);
        bk.setAmount(BigDecimal.valueOf(2_000_000));
        bk.setUser(UserDummies.users().get(0));
        bk.setFlightSchedule(fs);
        bk.setBookingStatus(BookingStatus.ACTIVE);
        bk.setCreatedAt(LocalDateTime.now());
        bk.setUpdatedAt(LocalDateTime.now());
        bk.setExpiredTime(System.currentTimeMillis() + 1800000L);
        bk.setPaymentMethod(PaymentDummies.paymentMethods().get(0));
        bk.setTotalPassenger(2);

        User usr = UserDummies.users().get(0);

        BookingValidateDTO bv1 = new BookingValidateDTO();
        bv1.setBookingId(bookingId);
        bv1.setUsername(usr.getFirstName().concat(" ").concat(usr.getLastName()));
        bv1.setUserId(usr.getId());
        bv1.setEmail(usr.getEmail());
        bv1.setPhoneNumber(usr.getPhoneNumber());
        bv1.setBookingStatus(BookingStatus.WAITING);
        bv1.setCreatedAt(LocalDateTime.now());
        bv1.setAmount(bk.getAmount());

        Pageable pageable = PageRequest.of(0, 10);

        PageImpl<BookingValidateDTO> page = new PageImpl<>(List.of(bv1));

        Mockito.when(bookingRepository.validateBookingList(BookingStatus.WAITING, pageable))
                .thenReturn(page);

        var actualValue = bookingService.validateBookingList(pageable);
        var expectedSize = 1;
        var expectedUserId = usr.getId();
        var expectedUsername = usr.getFirstName().concat(" ").concat(usr.getLastName());
        var expectedPhoneNumber = usr.getPhoneNumber();
        var expectedBookingStatus = BookingStatus.WAITING;
        var expectedAmount = bk.getAmount();

        Assertions.assertNotNull(actualValue);
        Assertions.assertEquals(expectedSize, actualValue.size());
        Assertions.assertEquals(expectedUserId, actualValue.get(0).getUserId());
        Assertions.assertEquals(expectedAmount, actualValue.get(0).getAmount());
        Assertions.assertEquals(expectedBookingStatus, actualValue.get(0).getBookingStatus());
        Assertions.assertEquals(expectedPhoneNumber, actualValue.get(0).getPhoneNumber());
        Assertions.assertEquals(expectedUsername, actualValue.get(0).getUsername());
    }

    @Test
    void testBookingStatus() {
        String[] randId = UUID.randomUUID().toString().toUpperCase().split("-");

        FlightRoute fr = FlightRouteDummies.flightRoute().get(0);

        AircraftDetail acd = AircraftDetailDummies.getAll().get(2);

        FlightSchedule fs = new FlightSchedule();
        fs.setId("sc-" + randId[0] + randId[1]);
        fs.setFlightRoute(fr);
        fs.setAircraftDetail(acd);
        fs.setStatus(Status.ACTIVE);
        fs.setArrivalTime(LocalDateTime.now());
        fs.setDepartureTime(LocalDateTime.now());
        fs.setCreatedAt(LocalDateTime.now());

        String[] uniqueId = UUID.randomUUID().toString().toUpperCase().split("-");
        String bookingId = "bk-" +  uniqueId[0] + uniqueId[1];
        Booking bk = new Booking();
        bk.setId(bookingId);
        bk.setAmount(BigDecimal.valueOf(2_000_000));
        bk.setUser(UserDummies.users().get(0));
        bk.setFlightSchedule(fs);
        bk.setBookingStatus(BookingStatus.COMPLETED);
        bk.setCreatedAt(LocalDateTime.now());
        bk.setUpdatedAt(LocalDateTime.now());
        bk.setExpiredTime(System.currentTimeMillis() + 1800000L);
        bk.setPaymentMethod(PaymentDummies.paymentMethods().get(0));
        bk.setTotalPassenger(2);

        Mockito.when(bookingRepository.checkStatus(bookingId))
                .thenReturn(Optional.of(bk));

        var actualValue = bookingService.bookingStatus(bookingId);
        var expectedBookingStatus = BookingStatus.COMPLETED;
        var expectedIsPaid = true;

        Assertions.assertNotNull(actualValue);
        Assertions.assertEquals(expectedBookingStatus, actualValue.getBookingStatus());
        Assertions.assertEquals(expectedIsPaid, actualValue.getIsPaid());
    }

    @Test
    void testFindByStatus() {
        String[] randId = UUID.randomUUID().toString().toUpperCase().split("-");

        FlightRoute fr = FlightRouteDummies.flightRoute().get(0);

        AircraftDetail acd = AircraftDetailDummies.getAll().get(2);

        FlightSchedule fs = new FlightSchedule();
        fs.setId("sc-" + randId[0] + randId[1]);
        fs.setFlightRoute(fr);
        fs.setAircraftDetail(acd);
        fs.setStatus(Status.ACTIVE);
        fs.setArrivalTime(LocalDateTime.now());
        fs.setDepartureTime(LocalDateTime.now());
        fs.setCreatedAt(LocalDateTime.now());

        String[] uniqueId = UUID.randomUUID().toString().toUpperCase().split("-");
        String bookingId = "bk-" +  uniqueId[0] + uniqueId[1];
        Booking bk = new Booking();
        bk.setId(bookingId);
        bk.setAmount(BigDecimal.valueOf(2_000_000));
        bk.setUser(UserDummies.users().get(0));
        bk.setFlightSchedule(fs);
        bk.setBookingStatus(BookingStatus.ACTIVE);
        bk.setCreatedAt(LocalDateTime.now());
        bk.setUpdatedAt(LocalDateTime.now());
        bk.setExpiredTime(System.currentTimeMillis() + 1800000L);
        bk.setPaymentMethod(PaymentDummies.paymentMethods().get(0));
        bk.setTotalPassenger(2);

        List<Booking> bkList = List.of(bk, bk, bk);

        User usr = UserDummies.users().get(0);

        List<BookingDTO> result = bkList.stream()
                .map(it -> {
                    BookingDTO bkDto = new BookingDTO();
                    bkDto.setBookingId(it.getId());
                    bkDto.setUserId(it.getUser().getId());
                    bkDto.setBookingStatus(it.getBookingStatus());
                    bkDto.setEmail(it.getUser().getEmail());
                    bkDto.setAmount(it.getAmount());
                    bkDto.setPhoneNumber(it.getUser().getPhoneNumber());
                    return bkDto;
                }).toList();


        PageImpl<BookingDTO> page = new PageImpl<>(result);
        Pageable pageable = PageRequest.of(0, 10);

        Mockito.when(bookingRepository.checkStatus(bookingId))
                .thenReturn(Optional.of(bk));
        Mockito.when(bookingRepository.findBookingStatus(BookingStatus.ACTIVE,  pageable))
                .thenReturn(page);

        var actualValue = bookingService.findByStatus("active", pageable);
        var expectedSize = 3;
        var expectedAmount = bk.getAmount();
        var expectedEmail = usr.getEmail();
        var expectedUserId = usr.getId();

        Assertions.assertNotNull(actualValue);
        Assertions.assertEquals(expectedSize, actualValue.size());
        Assertions.assertEquals(expectedEmail, actualValue.get(0).getEmail());
        Assertions.assertEquals(expectedAmount, actualValue.get(0).getAmount());
        Assertions.assertEquals(expectedUserId, actualValue.get(0).getUserId());
    }
}