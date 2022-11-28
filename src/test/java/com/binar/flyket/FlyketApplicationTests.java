package com.binar.flyket;

import com.binar.flyket.dto.model.AirportDetailDTO;
import com.binar.flyket.dto.model.FlightRouteDetailDTO;
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

	@Test
	void contextLoads() {
	}

	@Test
	void AirportDetail() {
		Optional<AirportDetailDTO> ls = repository.findByIATACode("JKT");
		System.out.println("City : " + ls.get().getCity());
		System.out.println("IATACode : " + ls.get().getIATACode());
		System.out.println("Name : " + ls.get().getName());
		System.out.println("Country : " + ls.get().getCountry());
	}

	@Test
	void AirportListDetail() {
		List<AirportDetailDTO> ls = repository.findAllAirport();

		System.out.println(ls.get(0).getCity());
		System.out.println(ls.get(0).getCountry());
		System.out.println(ls.get(0).getName());
		System.out.println(ls.get(0).getIATACode());

		System.out.println("Total Aiport : " + ls.size());
	}

	@Test
	void RouteListDetail() {
		List<FlightRouteDetailDTO> allRoute = routeRepository.findAllRoute();

		System.out.println(allRoute);
	}

}
