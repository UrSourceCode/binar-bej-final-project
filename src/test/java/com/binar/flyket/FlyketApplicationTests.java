package com.binar.flyket;


import com.binar.flyket.repository.AircraftDetailRepository;
import com.binar.flyket.repository.AirportRepository;
import com.binar.flyket.repository.AirportRouteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FlyketApplicationTests {


	@Autowired
	private AirportRepository repository;


	@Test
	void contextLoads() {}

	@Autowired
	private AirportRouteRepository routeRepository;

	@Autowired
	private AircraftDetailRepository aircraftDetailRepository;

}
