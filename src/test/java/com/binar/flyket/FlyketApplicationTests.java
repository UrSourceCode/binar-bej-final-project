package com.binar.flyket;

import com.binar.flyket.repository.AirportRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FlyketApplicationTests {


	@Autowired
	private AirportRepository repository;

	@Test
	void contextLoads() {}
}
