package com.binar.flyket;



import com.binar.flyket.dto.model.BookingDetailDTO;
import com.binar.flyket.dto.model.MyOrderDetailDTO;
import com.binar.flyket.repository.*;

import com.binar.flyket.dto.model.AvailableSeatDTO;
import com.binar.flyket.model.SeatDetail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class FlyketApplicationTests {

	@Test
	void contextLoads() {
	}

}
