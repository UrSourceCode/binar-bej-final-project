package com.binar.flyket.service;

import com.binar.flyket.dto.model.FlightScheduleDetailDTO;
import com.binar.flyket.dto.model.MyOrderDTO;
import com.binar.flyket.dto.model.PassengerTicketList;
import com.binar.flyket.dummy.AircraftDetailDummies;
import com.binar.flyket.dummy.FlightRouteDummies;
import com.binar.flyket.dummy.PaymentDummies;
import com.binar.flyket.dummy.UserDummies;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TransactionHistoryServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private FlightScheduleRepository flightScheduleRepository;

    @Mock
    private PaymentMethodRepository paymentMethodRepository;

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TransactionHistoryServiceImpl transactionHistoryService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testgetRecentOrder(){
        User user = UserDummies.users().get(0);
        Pageable pageable = PageRequest.of(0, 10);
        MyOrderDTO myOrderDTO = new MyOrderDTO();

        LocalDateTime departureTime=LocalDateTime.now();
        LocalDateTime ArrivalTime=LocalDateTime.now();

        myOrderDTO.setEmail(user.getEmail());
        myOrderDTO.setAmount(BigDecimal.valueOf(1000000));
        myOrderDTO.setDepartureTime(departureTime);
        myOrderDTO.setArrivalTime(ArrivalTime);
        myOrderDTO.setMinutes(20);
        myOrderDTO.setHours(1);
        myOrderDTO.setPrice(BigDecimal.valueOf(1000000));
        myOrderDTO.setBookingId("bk-9642AA2ECFE2");
        myOrderDTO.setFlightId("A02-wda");
        myOrderDTO.setOriginAirport("Jakarta");
        myOrderDTO.setPhoneNumber("082108210821");
        myOrderDTO.setPurchaseCompleteAt(departureTime);
        myOrderDTO.setUserId(user.getId());
        myOrderDTO.setTotalPassenger(1);
        myOrderDTO.setBookingStatus(BookingStatus.valueOf("COMPLETED"));
        myOrderDTO.setDestinationAirport("CGK");

        List<MyOrderDTO>myOrderDTOList = List.of(myOrderDTO,myOrderDTO);
        PageImpl<MyOrderDTO> page = new PageImpl<>(myOrderDTOList);
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(bookingRepository.getRecentOrderByUser(user.getId(),pageable)).thenReturn(page);

        var actualvalue = transactionHistoryService.getRecentOrder(user.getId(),pageable);
        var expectedHours = 1;
        var expectedMinutes = 20;
        var expectedFlightId = "A02-wda";
        var expectedUser = user.getId();
        var expectedTotalPassenger = 1;
        var expectedDestinationAirport = "CGK";
        var expectedBookingStatus = BookingStatus.valueOf("COMPLETED");
        var expectedPhoneNumber = "082108210821";
        var expectedPrice = BigDecimal.valueOf(1000000);
        var expectedAmount = BigDecimal.valueOf(1000000);
        var expectedEmail = user.getEmail();
        var expectedPurchaseCompleteAt = departureTime;
        var expectedBookingId = "bk-9642AA2ECFE2";
        var expectedArrivalTime = ArrivalTime;
        var expectedDepartureTime = departureTime;

        Assertions.assertEquals(2,actualvalue.size());
        Assertions.assertEquals(expectedHours,actualvalue.get(0).getHours());
        Assertions.assertEquals(expectedMinutes,actualvalue.get(0).getMinutes());
        Assertions.assertEquals(expectedUser,actualvalue.get(0).getUserId());
        Assertions.assertEquals(expectedTotalPassenger,actualvalue.get(0).getTotalPassenger());
        Assertions.assertEquals(expectedDestinationAirport,actualvalue.get(0).getDestinationAirport());
        Assertions.assertEquals(expectedBookingId,actualvalue.get(0).getBookingId());
        Assertions.assertEquals(expectedBookingStatus,actualvalue.get(0).getBookingStatus());
        Assertions.assertEquals(expectedPhoneNumber,actualvalue.get(0).getPhoneNumber());
        Assertions.assertEquals(expectedPrice,actualvalue.get(0).getPrice());
        Assertions.assertEquals(expectedAmount,actualvalue.get(0).getAmount());
        Assertions.assertEquals(expectedEmail,actualvalue.get(0).getEmail());
        Assertions.assertEquals(expectedPurchaseCompleteAt,actualvalue.get(0).getPurchaseCompleteAt());
        Assertions.assertEquals(expectedArrivalTime,actualvalue.get(0).getArrivalTime());
        Assertions.assertEquals(expectedDepartureTime,actualvalue.get(0).getDepartureTime());
        Assertions.assertEquals(expectedFlightId,actualvalue.get(0).getFlightId());
    }

    @Test
    void testgetRecentOrderDetail(){
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

            PassengerTicketList passengerTicketList = new PassengerTicketList();
            passengerTicketList.setPassengerName("Agam");
            passengerTicketList.setTicketId("A02-wda");
            passengerTicketList.setSeatNo("A1");
            passengerTicketList.setPassengerTitle("Mr");

            Mockito.when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(bk));
            Mockito.when(ticketRepository.getRecentOrderDetail(bookingId)).thenReturn(List.of(passengerTicketList));

            var actualvalue = transactionHistoryService.getRecentOrderDetail(bk.getId());
            var expectedbookingId = bk.getId();
            var expectedPassengerName = "Agam";
            var expectedPassengerTittle = "Mr";
            var expectedTicketId = "A02-wda";
            var expectedSeatNo = "A1";

            Assertions.assertEquals(expectedPassengerName,actualvalue.getPassengerTicketLists().get(0).getPassengerName());
            Assertions.assertEquals(expectedPassengerTittle,actualvalue.getPassengerTicketLists().get(0).getPassengerTitle());
            Assertions.assertEquals(expectedTicketId,actualvalue.getPassengerTicketLists().get(0).getTicketId());
            Assertions.assertEquals(expectedSeatNo,actualvalue.getPassengerTicketLists().get(0).getSeatNo());
    }



    @Test
    void getAllBookingHistory(){

    }
}
