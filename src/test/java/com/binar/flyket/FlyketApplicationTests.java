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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class FlyketApplicationTests {

	@Test
	void contextLoads() {
		String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d#$@!%&*?\\s]{8,}$";
		String password = "Riswan123";
		Pattern pattern = Pattern.compile(passwordPattern);
		Matcher matcher = pattern.matcher(password);

		System.out.println(matcher.matches() ? "OK" : "NO");
	}
}
