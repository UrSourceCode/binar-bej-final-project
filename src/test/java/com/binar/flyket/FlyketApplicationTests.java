package com.binar.flyket;

import 	com.binar.flyket.dto.model.AirportDetailDTO;
import com.binar.flyket.repository.AirportRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class FlyketApplicationTests {


	@Autowired
	private AirportRepository repository;

	@Test
	void contextLoads() {}
}
