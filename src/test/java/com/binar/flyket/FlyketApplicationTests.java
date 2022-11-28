package com.binar.flyket;

import com.binar.flyket.dto.model.AircraftDetailDTO;
import com.binar.flyket.dto.model.AirportDetailDTO;
import com.binar.flyket.dto.model.FlightRouteDetailDTO;
import com.binar.flyket.repository.AircraftDetailRepository;
import com.binar.flyket.repository.AirportRepository;
import com.binar.flyket.repository.AirportRouteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class FlyketApplicationTests {


	@Autowired
	private AirportRepository repository;

	@Autowired
	private AirportRouteRepository routeRepository;

	@Autowired
	private AircraftDetailRepository aircraftDetailRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void aircraftDetail() {

	}

}
