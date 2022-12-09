package com.binar.flyket;



import com.binar.flyket.dto.model.AircraftDetailDTO;
import com.binar.flyket.dto.model.AirportDetailDTO;
import com.binar.flyket.dto.model.FlightRouteDetailDTO;
import com.binar.flyket.model.Booking;
import com.binar.flyket.model.Seat;
import com.binar.flyket.model.SeatNo;
import com.binar.flyket.repository.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.*;

@SpringBootTest
class FlyketApplicationTests {


	@Autowired
	private AirportRepository repository;

	@Autowired
	private SeatRepository seatRepository;

	@Autowired
	private BookingRepository bookingRepository;


	@Test
	void contextLoads() {
		Booking booking = new Booking();

		booking.setId("hello-ad");
		booking.setAmount(BigDecimal.valueOf(1000000000));
		booking.setExpiredTime(10000000L);

	}

}
