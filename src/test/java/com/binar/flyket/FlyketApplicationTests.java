package com.binar.flyket;


import com.binar.flyket.model.SeatDetail;
import com.binar.flyket.repository.SeatDetailRepository;
import com.binar.flyket.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class FlyketApplicationTests {


	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private SeatDetailRepository seatDetailRepository;


	@Test
	void contextLoads() {

	}

}
